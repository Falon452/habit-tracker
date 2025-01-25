package com.falon.habit.presentation.habit.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.falon.habit.domain.HabitCounter
import com.falon.habit.domain.HabitCounter.HabitCounterDataClass
import com.falon.habit.presentation.MyApplicationTheme
import com.falon.habit.presentation.habit.viewmodel.AndroidHabitsViewModel

import androidx.compose.material.minimumInteractiveComponentSize

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
    var isSearchPopupVisible by remember { mutableStateOf(false) }
    var rowid by remember { mutableStateOf(0) }
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
                onEditHabitClicked = { isSearchPopupVisible = true },
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
        SearchPopup(
            isVisible = isSearchPopupVisible,
            onDismiss = { isSearchPopupVisible = false },
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
    habitItems: List<HabitCounterDataClass>,
    onSocialMediaClicked: (UInt) -> Unit,
    onEditHabitClicked: (UInt) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(habitItems.size) { index ->
            val item = habitItems[index]
            ClickableCard(
                item = habitItems[index],
                onClick = { onSocialMediaClicked(item.id) },
                onLongClick = { onEditHabitClicked(item.id) }
            )
        }
    }
}

fun onEditHabitClicked(id: UInt) {

}

fun mockDatabaseSearch(query: String): List<String> {
    // Example list simulating database entries
    val exampleDatabase = listOf(
        "john.doe@example.com",
        "jane.doe@example.com",
        "alice.smith@example.com",
        "bob.jones@example.com",
        "carol.wilson@example.com",
        "dave.brown@example.com",
        "emily.clark@example.com",
        "frank.thomas@example.com"
    )
    return exampleDatabase.filter { it.contains(query, ignoreCase = true) }
}


@Composable
fun SearchPopup(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSearch: (String) -> List<String> = ::mockDatabaseSearch
) {
    if (isVisible) {
        Dialog(onDismissRequest = { onDismiss() }) {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colors.surface
                ) {
                    var searchQuery by remember { mutableStateOf("") }
                    var searchResults by remember { mutableStateOf(listOf<String>()) }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Share habit",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = MaterialTheme.colors.onSurface
                        )

                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Enter search query") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.surface
                            )
                        )

                        Button(
                            onClick = { searchResults = onSearch(searchQuery) },
                            modifier = Modifier.align(Alignment.End),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text("Search", color = MaterialTheme.colors.onPrimary)
                        }

                        if (searchResults.isNotEmpty()) {
                            Text(
                                text = "Results:",
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(top = 8.dp),
                                color = MaterialTheme.colors.onSurface
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(top = 8.dp)
                            ) {
                                for (result in searchResults) {
                                    item {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
//                                            Text(
//                                                text = result,
//                                                modifier = Modifier.weight(1f),
//                                                style = MaterialTheme.typography.body1,
//                                                color = MaterialTheme.colors.onSurface
//                                            )

                                            Button(
                                                onClick = {
                                                    println("Sharing with $result")
                                                },
                                                modifier = Modifier.padding(start = 8.dp)
                                            ) {
                                                Text("Share with $result")
                                            }
                                        }
                                    }
                                }
                            }

                        } else {
                            Text(
                                text = "No results found.",
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(top = 8.dp),
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClickableCard(item: HabitCounter, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .padding(8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick, // Pass single click handler
                onLongClick = onLongClick // Pass long click handler
            ),
        elevation = 8.dp
    ) {
        Box {
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

