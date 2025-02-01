package com.falon.habit.presentation.habit.ui

import android.widget.Toast
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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.minimumInteractiveComponentSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.falon.habit.presentation.Colors
import com.falon.habit.presentation.habit.viewmodel.AndroidHabitsViewModel
import com.falon.habit.presentation.model.HabitItem

@Composable
internal fun HabitsScreen(
    viewModel: AndroidHabitsViewModel = hiltViewModel(),
) {
    val viewState by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()

    HandleEffects(viewModel, focusRequester)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = { FloatingActionButton(viewModel::onFabClick) },
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
                .background(MaterialTheme.colors.background),
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
    listState: LazyListState
) {
    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val firstItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    val titleHeight by animateFloatAsState(
        targetValue = if (firstItemIndex.asIntState().intValue > 0 || scrollOffset.asIntState().intValue > 100) 0f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
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
                    style = MaterialTheme.typography.h4.copy(
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
                                    Color(Colors.LifeBlue),
                                    Color(Colors.LifeGrey)
                                )
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 8.dp * titleHeight)
                )
            }
        }

        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
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
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var emailInput by remember { mutableStateOf("") }

                Text(
                    text = "Share habit with",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colors.onSurface
                )

                TextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = { Text("Enter e-mail") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    )
                )

                Button(
                    onClick = { onShareHabitWith(emailInput) },
                    modifier = Modifier.align(Alignment.End),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Search", color = MaterialTheme.colors.onPrimary)
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

@Composable
fun HandleEffects(
    viewModel: AndroidHabitsViewModel,
    focusRequester: FocusRequester
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collect {
                viewModel.onEffect(
                    viewModel.consumeEffect(),
                    keyboardController,
                    focusRequester,
                    showToast = { text ->
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ClickableCard(item: HabitItem, onClick: (id: String) -> Unit, onLongClick: (id: String) -> Unit) {
    Card(
        onClick = {
            onClick.invoke(item.id)
        },
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
        elevation = if (item.isEnabled) 8.dp else 0.dp,
        enabled = item.isEnabled,
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

