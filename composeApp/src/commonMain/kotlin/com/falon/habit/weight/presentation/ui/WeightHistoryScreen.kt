package com.falon.habit.weight.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.falon.habit.Routes
import com.falon.habit.habits.presentation.ui.BottomNavigationBar
import com.falon.habit.utils.showToast
import com.falon.habit.weight.presentation.viewmodel.WeightHistoryViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightHistoryScreen(
    navController: NavController,
    viewModel: WeightHistoryViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val scrollState = rememberScrollState()

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, viewModel.events) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                viewModel.onEvent(event, ::showToast)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Weight History") }) },
        bottomBar = { BottomNavigationBar(navController, Routes.WeightsHistoryScreen) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            ExpandableCard(
                title = "Current Measurements",
                onExpandableCardClicked = { viewModel.onCurrentMeasurementsClicked() },
                expanded = state.isCurrentMeasurementsExpanded,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    MyTextField(
                        inputField = state.weight,
                        isError = state.weightError,
                        errorMessage = state.weightErrorMessage,
                        onInput = { viewModel.onWeightInputChanged(it) },
                        label = "Weight (kg)"
                    )
                    MyTextField(
                        inputField = state.fat,
                        isError = state.fatError,
                        errorMessage = state.fatErrorMessage,
                        onInput = { viewModel.onFatInputChanged(it) },
                        label = "Fat (%)"
                    )
                    MyTextField(
                        inputField = state.muscle,
                        isError = state.muscleError,
                        errorMessage = state.muscleErrorMessage,
                        onInput = { viewModel.onMuscleInputChanged(it) },
                        label = "Muscle (%)"
                    )
                    MyTextField(
                        inputField = state.water,
                        isError = state.waterError,
                        errorMessage = state.waterErrorMessage,
                        onInput = { viewModel.onWaterInputChanged(it) },
                        label = "Water (%)"
                    )
                    MyTextField(
                        inputField = state.bmi,
                        isError = state.bmiError,
                        errorMessage = state.bmiErrorMessage,
                        onInput = { viewModel.onBmiInputChanged(it) },
                        label = "BMI"
                    )
                    MyTextField(
                        inputField = state.bones,
                        isError = state.bonesError,
                        errorMessage = state.bonesErrorMessage,
                        onInput = { viewModel.onBonesInputChanged(it) },
                        label = "Bones (kg)"
                    )
                    SaveButton { viewModel.onSaveClicked(fromCurrentMeasurement = true) }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            ExpandableCard(
                title = "Goals",
                onExpandableCardClicked = { viewModel.onGoalsClicked() },
                expanded = state.isGoalsExpanded,
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    MyTextField(
                        inputField = state.weightGoal,
                        isError = state.weightGoalError,
                        errorMessage = state.weightGoalErrorMessage,
                        onInput = { viewModel.onWeightGoalInputChanged(it) },
                        label = "Weight Goal (kg)"
                    )
                    MyTextField(
                        inputField = state.fatGoal,
                        isError = state.fatGoalError,
                        errorMessage = state.fatGoalErrorMessage,
                        onInput = { viewModel.onFatGoalInputChanged(it) },
                        label = "Fat Goal (%)"
                    )
                    MyTextField(
                        inputField = state.muscleGoal,
                        isError = state.muscleGoalError,
                        errorMessage = state.muscleGoalErrorMessage,
                        onInput = { viewModel.onMuscleGoalInputChanged(it) },
                        label = "Muscle Goal (%)"
                    )
                    MyTextField(
                        inputField = state.waterGoal,
                        isError = state.waterGoalError,
                        errorMessage = state.waterGoalErrorMessage,
                        onInput = { viewModel.onWaterGoalInputChanged(it) },
                        label = "Water Goal (%)"
                    )
                    MyTextField(
                        inputField = state.bmiGoal,
                        isError = state.bmiGoalError,
                        errorMessage = state.bmiGoalErrorMessage,
                        onInput = { viewModel.onBmiGoalInputChanged(it) },
                        label = "BMI Goal"
                    )
                    MyTextField(
                        inputField = state.bonesGoal,
                        isError = state.bonesGoalError,
                        errorMessage = state.bonesGoalErrorMessage,
                        onInput = { viewModel.onBonesGoalInputChanged(it) },
                        label = "Bones Goal (kg)"
                    )
                    SaveButton { viewModel.onSaveClicked(fromCurrentMeasurement = false) }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            ChartCard(
                title = "Weight Progress",
                x = state.weightX,
                y = state.weightY,
                goal = state.weightGoalY,
                markerSuffix = " kg",
                verticalAxisSuffix = " kg",
                minY = state.weightMinY,
                maxY = state.weightMaxY
            )
            ChartCard(
                title = "Fat Progress",
                x = state.fatX,
                y = state.fatY,
                goal = state.fatGoalY,
                markerSuffix = " %",
                verticalAxisSuffix = " %",
                minY = state.fatMinY,
                maxY = state.fatMaxY
            )
            ChartCard(
                title = "Muscle Progress",
                x = state.muscleX,
                y = state.muscleY,
                goal = state.muscleGoalY,
                markerSuffix = " %",
                verticalAxisSuffix = " %",
                minY = state.muscleMinY,
                maxY = state.muscleMaxY
            )
            ChartCard(
                title = "Water Progress",
                x = state.waterX,
                y = state.waterY,
                goal = state.waterGoalY,
                markerSuffix = " %",
                verticalAxisSuffix = " %",
                minY = state.waterMinY,
                maxY = state.waterMaxY
            )
            ChartCard(
                title = "Bones Progress",
                x = state.bonesX,
                y = state.bonesY,
                goal = state.bonesGoalY,
                markerSuffix = " kg",
                verticalAxisSuffix = " kg",
                minY = state.bonesMinY,
                maxY = state.bonesMaxY
            )
        }
    }
}

@Composable
fun ChartCard(
    title: String,
    x: List<Number>,
    y: List<Number>,
    goal: Number?,
    markerSuffix: String,
    verticalAxisSuffix: String,
    minY: Double,
    maxY: Double
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface.copy(alpha = 0.2F)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .background(gradientBrush)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Chart(
                    x = x,
                    y = y,
                    goal = goal,
                    markerSuffix = markerSuffix,
                    verticalAxisSuffix = verticalAxisSuffix,
                    minY = minY,
                    maxY = maxY
                )
            }
        }
    }
}
@Composable
fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = "Save",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ExpandableCard(
    title: String,
    expanded: Boolean,
    onExpandableCardClicked: () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandableCardClicked() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
            }
            if (expanded) {
                content()
            }
        }
    }
}
