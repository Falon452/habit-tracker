package com.falon.habit.weight.presentation.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.falon.habit.Routes
import com.falon.habit.habits.presentation.ui.BottomNavigationBar
import com.falon.habit.utils.showToast
import com.falon.habit.weight.presentation.viewmodel.WeightHistoryViewModel
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.legend.LegendLocation
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.DefaultPoint
import io.github.koalaplot.core.xygraph.FloatLinearAxisModel
import io.github.koalaplot.core.xygraph.LongLinearAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.XYGraphScope
import io.github.koalaplot.core.xygraph.rememberAxisStyle
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

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
            ExpandableCard(title = "Current Measurements", false) {
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
                    SaveButton { viewModel.onSaveClicked() }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            ExpandableCard(title = "Goals", false) {
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
                    SaveButton { viewModel.onSaveClicked() }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            //
            ScrollableLineChart()
        }
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun ScrollableLineChart() {
    val data = remember {
        mutableStateListOf(
            DefaultPoint(
                Clock.System.now().epochSeconds,
                Random.nextFloat()
            )
        )
    }
    var yDataMin by remember { mutableStateOf(0f) }
    var yDataMax by remember { mutableStateOf(1f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Button(
            onClick = {
                data.last().y
                val yNext = Random.nextFloat() * 10F // Dummy float value for the y-axis
                data.add(DefaultPoint(Clock.System.now().epochSeconds, yNext))
                yDataMin = min(yDataMin, yNext)
                yDataMax = max(yDataMax, yNext)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Add Data Point")
        }

        ChartLayout(
            modifier = Modifier.padding(16.dp),
            title = { Text("Time Chart") },
            legendLocation = LegendLocation.BOTTOM
        ) {
            XYGraph(
                xAxisModel = LongLinearAxisModel(
                    range = data.first().x..data.last().x + 1
                ),
                yAxisModel = FloatLinearAxisModel(
                    range = (yDataMin)..(yDataMax)
                ),
                xAxisLabels = {
                    val timestamp = Instant.fromEpochSeconds(it)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                    Text(timestamp.toString(), modifier = Modifier.padding(top = 2.dp))
                },
                xAxisStyle = rememberAxisStyle(labelRotation = 90),
                xAxisTitle = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Time")
                    }
                },
                yAxisLabels = {
                    Text(it.toString(), Modifier.absolutePadding(right = 2.dp))
                },
                yAxisTitle = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text("Value", modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
            ) {
                chart(data)
            }
        }
    }
}

@Composable
private fun XYGraphScope<Long, Float>.chart(data: List<DefaultPoint<Long, Float>>) {
    LinePlot(
        data = data,
        lineStyle = LineStyle(
            brush = SolidColor(Color.Black),
            strokeWidth = 2.dp
        )
    )
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
fun ExpandableCard(title: String, initiallyExpanded: Boolean, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

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
                    .clickable { expanded = !expanded }
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
