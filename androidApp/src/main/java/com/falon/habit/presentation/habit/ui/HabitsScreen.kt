package com.falon.habit.presentation.habit.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.falon.habit.domain.HabitCounter
import com.falon.habit.domain.HabitCounter.HabitCounterDataClass
import com.falon.habit.presentation.habit.viewmodel.AndroidHabitsViewModel


@Composable
fun HabitsScreen(
    viewModel: AndroidHabitsViewModel = hiltViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val viewState by produceState(
        initialValue = viewModel.state.value,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.state.collect {
                value = it
            }
        }
    }
    val focusRequester = remember { FocusRequester() }

    HandleEffects(viewModel, focusRequester)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(floatingActionButton = { FloatingActionButton(viewModel::onFabClick) }) { padding ->
            HabitsColumn(
                habitItems = viewState.habitCounters,
                onSocialMediaClicked = viewModel::onSocialMediaClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            )
        }

        BottomSheetDialog(
            viewState.isBottomDialogVisible,
            viewState.bottomDialogText,
            viewModel::onNewHabitTextChanged,
            viewModel::onNewHabit,
            focusRequester,
            Modifier
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colors.background),
        )
    }
}

@Composable
private fun BottomSheetDialog(
    isBottomDialogVisible: Boolean,
    bottomDialogText: String,
    onNewHabitTextChanged: (String) -> Unit,
    onNewHabit: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isBottomDialogVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            NewHabitTextField(
                bottomDialogText = bottomDialogText,
                onNewHabitTextChanged = onNewHabitTextChanged,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .weight(1F)
            )
            NewHabitButton(onNewHabit)
        }
    }
}

@Composable
private fun NewHabitTextField(
    bottomDialogText: String,
    onNewHabitTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = bottomDialogText,
        onValueChange = onNewHabitTextChanged,
        modifier = modifier,
        placeholder = { Text("New habit") }
    )
}

@Composable
private fun NewHabitButton(
    onNewHabit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = onNewHabit,
    ) {
        Text("Save")
    }
}

@Composable
private fun HabitsColumn(
    habitItems: List<HabitCounterDataClass>,
    onSocialMediaClicked: (UInt) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(habitItems.size) { index ->
            val item = habitItems[index]
            ClickableCard(
                item = habitItems[index],
                onClick = { onSocialMediaClicked(item.id) }
            )
        }
    }
}

@Composable
private fun FloatingActionButton(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClick,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Social Media")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HandleEffects(
    viewModel: AndroidHabitsViewModel,
    focusRequester: FocusRequester
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collect {
                viewModel.onEffect(
                    viewModel.consumeEffect(),
                    keyboardController,
                    focusRequester,
                )
            }
        }
    }
}

@Composable
fun ClickableCard(item: HabitCounter, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        elevation = 8.dp
    ) {
        Box {
            Text(
                text = item.name.value,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
                    .padding(4.dp)
            )
            Text(
                text = item.numberOfDays.toString(),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
                    .padding(4.dp)
            )
        }
    }
}
