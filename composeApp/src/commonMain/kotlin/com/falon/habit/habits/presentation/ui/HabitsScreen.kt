package com.falon.habit.habits.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.falon.habit.habits.presentation.model.HabitItem
import com.falon.habit.habits.presentation.viewmodel.HabitsViewModel


@Composable
internal fun HabitsScreen(
    viewModel: HabitsViewModel,
    showToast: (String) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()

    HandleEffects(viewModel, showToast)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = { HabitFloatingActionButton(viewModel::onFabClick) },
            topBar = {
                CollapsingTitle(
                    title = "Habits",
                    listState = listState
                )
            }
        ) { padding ->
            HabitsColumn(
                listState = listState,
                habitItems = viewState.habitItems,
                onHabitClicked = viewModel::onHabitClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                onHabitLongClicked = viewModel::onHabitLongClicked,
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
                .background(MaterialTheme.colorScheme.background),
        )
        ShareDialog(
            isVisible = viewState.isShareHabitDialogVisible,
            onDismiss = viewModel::onShareHabitDialogDismiss,
            onShareHabitWith = viewModel::onShareHabitWith,
        )
    }
}

@Composable
fun CollapsingTitle(
    title: String,
    listState: LazyListState,
) {
    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    val titleHeight by animateFloatAsState(
        targetValue = if (firstItemIndex.asIntState().intValue > 0 || scrollOffset.asIntState().intValue > 100) 0f else 1f,
        animationSpec = tween(durationMillis = 300), label = "Animating Habits"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (titleHeight > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp * titleHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = (32.sp * titleHeight),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .shadow(
                            8.dp * titleHeight,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                )
                            ),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 8.dp * titleHeight)
                )
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
                .combinedClickable(
                    onClick = {},
                    onLongClick = {},
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
    habitItems: List<HabitItem>,
    onHabitClicked: (String) -> Unit,
    onHabitLongClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        items(habitItems.size) { index ->
            ClickableCard(
                item = habitItems[index],
                onClick = onHabitClicked,
                onLongClick = onHabitLongClicked,
            )
        }
    }
}

@Composable
fun ShareDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onShareHabitWith: (email: String) -> Unit,
) {
    if (isVisible) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var emailInput by remember { mutableStateOf("") }

                Text(
                    text = "Share habit with",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                TextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = { Text("Enter e-mail") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Button(
                    onClick = { onShareHabitWith(emailInput) },
                    modifier = Modifier.align(Alignment.End),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Search", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}


@Composable
private fun HabitFloatingActionButton(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClick,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
        ),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Habit",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}


@Composable
fun HandleEffects(
    viewModel: HabitsViewModel,
    showToast: (text: String) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            viewModel.effects.collect {
                viewModel.onEffect(
                    viewModel.consumeEffect(),
                    keyboardController,
                    showToast = { showToast(it) }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClickableCard(
    item: HabitItem,
    onClick: (id: String) -> Unit,
    onLongClick: (id: String) -> Unit,
) {
    Card(
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .fillMaxWidth()
            .padding(8.dp)
            .alpha(if (item.isEnabled) 1f else 0.5f)
            .combinedClickable(
                enabled = item.isEnabled,
                onClick = { onClick.invoke(item.id) },
                onLongClick = { onLongClick.invoke(item.id) }
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (item.isEnabled) 2.dp else 0.dp
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp),
        ) {
            Text(
                text = item.name.value,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
            )
            Text(
                text = item.numberOfDays.toString(),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
            )
        }
    }
}
