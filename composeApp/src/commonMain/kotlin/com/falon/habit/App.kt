package com.falon.habit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falon.habit.habits.presentation.ui.AppTheme
import com.falon.habit.habits.presentation.ui.HabitsScreen
import com.falon.habit.login.presentation.ui.LoginScreen
import com.falon.habit.register.presentation.ui.RegisterScreen
import com.falon.habit.splash.presentation.ui.SplashScreen
import com.falon.habit.weight.presentation.ui.WeightHistoryScreen
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.Scroll
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.multiplatform.cartesian.data.columnSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.CartesianLayerPadding
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.LayeredComponent
import com.patrykandpatrick.vico.multiplatform.common.LegendItem
import com.patrykandpatrick.vico.multiplatform.common.component.ShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.TextComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberLineComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import com.patrykandpatrick.vico.multiplatform.common.data.ExtraStore
import com.patrykandpatrick.vico.multiplatform.common.fill
import com.patrykandpatrick.vico.multiplatform.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import com.patrykandpatrick.vico.multiplatform.common.shape.MarkerCorneredShape
import com.patrykandpatrick.vico.multiplatform.common.vicoTheme
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import org.koin.compose.KoinContext


@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun App() {
    KoinContext {
        val navController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            AppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.SplashScreen,
                ) {
                    composable(route = Routes.SplashScreen) {
                        SplashScreen(navController)
                    }
                    composable(route = Routes.HabitsScreen) {
                        HabitsScreen(navController)
                    }
                    composable(route = Routes.LoginScreen) {
                        LoginScreen(navController = navController)
                    }
                    composable(route = Routes.RegisterScreen) {
                        RegisterScreen(navController = navController)
                    }
                    composable(route = Routes.WeightsHistoryScreen) {
                        WeightHistoryScreen(navController)
                    }
                }
            }
        }
    }
}


@Composable
fun ComposeMultiplatformBasicLineChart(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/z5ah6v.
            lineSeries { series(13, 8, 7, 12, 0, 1, 15, 14, 0, 11, 6, 12, 0, 11, 12, 11) }
        }
    }
    CartesianChartHost(
        scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.End),
        chart =
        rememberCartesianChart(
            rememberLineCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(),
        ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

private val LegendLabelKey = ExtraStore.Key<Set<String>>()

private val StartAxisValueFormatter = CartesianValueFormatter.decimal(suffix = " h")

private val StartAxisItemPlacer = VerticalAxis.ItemPlacer.step({ 0.5 })

private val MarkerValueFormatter = DefaultCartesianMarker.ValueFormatter.default(suffix = " h")

private val x = (2008..2018).toList()

private val y =
    mapOf(
        "Laptop/desktop" to listOf<Number>(2.2, 2.3, 2.4, 2.6, 2.5, 2.3, 2.2, 2.2, 2.2, 2.1, 2),
        "Mobile" to listOf(0.3, 0.3, 0.4, 0.8, 1.6, 2.3, 2.6, 2.8, 3.1, 3.3, 3.6),
        "Other" to listOf(0.2, 0.3, 0.4, 0.3, 0.3, 0.3, 0.3, 0.4, 0.4, 0.6, 0.7),
    )

@Composable
fun ComposeMultiplatformDailyDigitalMediaUse(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/3aqy4o.
            columnSeries { y.values.forEach { series(x, it) } }
            extras { it[LegendLabelKey] = y.keys }
        }
    }
    val columnColors = listOf(Color(0xff6438a7), Color(0xff3490de), Color(0xff73e8dc))
    val legendItemLabelComponent = rememberTextComponent(TextStyle(vicoTheme.textColor))
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider =
                ColumnCartesianLayer.ColumnProvider.series(
                    columnColors.map { color ->
                        rememberLineComponent(fill = fill(color), thickness = 16.dp)
                    }
                ),
                columnCollectionSpacing = 32.dp,
                mergeMode = { ColumnCartesianLayer.MergeMode.Stacked },
            ),
            startAxis =
            VerticalAxis.rememberStart(
                valueFormatter = StartAxisValueFormatter,
                itemPlacer = StartAxisItemPlacer,
            ),
            bottomAxis =
            HorizontalAxis.rememberBottom(
                itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() }
            ),
            marker = rememberMarker(MarkerValueFormatter),
            layerPadding = {
                CartesianLayerPadding(
                    scalableStart = 16.dp,
                    scalableEnd = 16.dp
                )
            },
            legend =
            rememberHorizontalLegend(
                items = { extraStore ->
                    extraStore[LegendLabelKey].forEachIndexed { index, label ->
                        add(
                            LegendItem(
                                ShapeComponent(fill(columnColors[index]), CorneredShape.Pill),
                                legendItemLabelComponent,
                                label,
                            )
                        )
                    }
                },
                padding = Insets(top = 16.dp),
            ),
        ),
        modelProducer = modelProducer,
        modifier = modifier.height(248.dp),
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}


@Composable
fun rememberMarker(
    valueFormatter: DefaultCartesianMarker.ValueFormatter =
        DefaultCartesianMarker.ValueFormatter.default(),
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = MarkerCorneredShape(CorneredShape.Corner.Rounded)
    val labelBackground =
        rememberShapeComponent(
            fill = fill(MaterialTheme.colorScheme.background),
            shape = labelBackgroundShape,
            strokeFill = fill(MaterialTheme.colorScheme.outline),
            strokeThickness = 1.dp,
        )
    val label =
        rememberTextComponent(
            style =
            TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
            ),
            padding = Insets(8.dp, 4.dp),
            background = labelBackground,
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    val indicatorFrontComponent =
        rememberShapeComponent(fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
    val guideline = rememberAxisGuidelineComponent()
    return rememberDefaultCartesianMarker(
        label = label,
        valueFormatter = valueFormatter,
        indicator =
        if (showIndicator) {
            { color ->
                LayeredComponent(
                    back = ShapeComponent(Fill(color.copy(alpha = 0.15f)), CorneredShape.Pill),
                    front =
                    LayeredComponent(
                        back = ShapeComponent(fill = Fill(color), shape = CorneredShape.Pill),
                        front = indicatorFrontComponent,
                        padding = Insets(5.dp),
                    ),
                    padding = Insets(10.dp),
                )
            }
        } else {
            null
        },
        indicatorSize = 36.dp,
        guideline = guideline,
    )
}
//
//@Composable
//fun StraightLineChart(pointsData: List<Point>) {
//    val textMeasurer = rememberTextMeasurer()
//
//    val steps = pointsData.size
//    val xAxisProperties = AxisProperties(
//        font = MaterialTheme.typography.bodyLarge.fontFamily,
//        stepSize = 40.dp,
//        stepCount = pointsData.size - 1,
//        labelFormatter = { i -> i.toString() },
//        labelRotationAngle = 20f,
//        labelPadding = 15.dp,
//        labelColor = Color.Red,
//        position = Gravity.RIGHT,
//        lineColor = Color.Red,
//        weight = FontWeight.Bold,
//    )
//
//    val yMin = pointsData.minOf { it.y }
//    val yMax = pointsData.maxOf { it.y }
//    val yScale = (yMax - yMin) / steps
//
//    val yAxisProperties = AxisProperties(
//        font = MaterialTheme.typography.bodyLarge.fontFamily,
//        stepCount = steps,
//        labelFormatter = { i -> ((i * yScale) + yMin).toString() },
//        labelPadding = 30.dp,
//        labelColor = Color.Red,
//        lineColor = Color.Red,
//        weight = FontWeight.Bold,
//    )
//
//    val lineChartProperties = LineChartProperties(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsData,
//                    lineStyle = LineStyle(lineType = LineType.Straight(), color = Color.Red),
//                    intersectionPoint = IntersectionPoint(color = Color.Red),
//                    selectionHighlightPopUp = SelectionHighlightPopUp(
//                        popUpLabel = { x, y ->
//                            val xLabel = "x : ${x.toInt()} "
//                            val yLabel = "y : ${y.formatNumber()}"
//                            "$xLabel $yLabel"
//                        },
//                        textMeasurer = textMeasurer,
//                        backgroundColor = Color.Red,
//                        labelColor = Color.Red,
//                        labelTypeface = FontWeight.Bold
//                    )
//                ),
//                Line(
//                    dataPoints = listOf(
//                        Point(1.0f, 76f, "65 4 point"),
//                        Point(13.0f, 76f, "65 4 point"),
//                    ),
//                    lineStyle = LineStyle(lineType = LineType.Straight(), color = Color.Blue),
//                    intersectionPoint = IntersectionPoint(color = Color.Blue),
//                    selectionHighlightPopUp = SelectionHighlightPopUp(
//                        popUpLabel = { x, y ->
//                            val xLabel = "x : ${x.toInt()} "
//                            val yLabel = "y : ${y.formatNumber()}"
//                            "$xLabel $yLabel"
//                        },
//                        textMeasurer = textMeasurer,
//                        backgroundColor = Color.Red,
//                        labelColor = Color.Red,
//                        labelTypeface = FontWeight.Bold
//                    )
//                ),
//            )
//        ),
//        xAxisProperties = xAxisProperties,
//        yAxisProperties = yAxisProperties
//    )
//    LineChart(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp),
//        lineChartProperties = lineChartProperties
//    )
//}
//@Composable
//fun SingleLineChartWithGridLines(pointsData: List<Point>, referenceWeight: Float) {
//    val textMeasurer = rememberTextMeasurer()
//    val steps = 5
//
//    val xAxisProperties = AxisProperties(
//        font = MaterialTheme.typography.bodyLarge.fontFamily,
//        weight = FontWeight.Medium,
//        stepSize = 30.dp,
//        topPadding = 120.dp, // Increased for better spacing
//        labelColor = MaterialTheme.colorScheme.onBackground,
//        lineColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f), // Slight transparency
//        stepCount = pointsData.size - 1,
//        labelFormatter = { i: Int ->
//            println(i)
//            pointsData[i].x.toInt().toString() },
//        labelPadding = 20.dp
//    )
//    val allYValues = pointsData.map { it.y } + referenceWeight // Include reference weight
//    val yMin = allYValues.min() - 5f // 5 kg below the lowest value
//    val yMax = allYValues.max() + 5f // 5 kg above the highest value
//    val yScale = (yMax - yMin) / steps
//
//    val yAxisProperties = AxisProperties(
//        font = MaterialTheme.typography.bodyLarge.fontFamily,
//        weight = FontWeight.Medium,
//        stepCount = steps,
//        labelColor = MaterialTheme.colorScheme.onBackground,
//        lineColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
//        labelPadding = 25.dp,
//        labelFormatter = { i -> ((i * yScale) + yMin).formatToSinglePrecision() }
//    )
//
//    // Horizontal Reference Line (e.g., goal weight)
//    val horizontalLine = Line(
//        dataPoints = listOf(
//            Point(1F, referenceWeight),
//            Point(2F, referenceWeight),
//            Point(3F, referenceWeight),
//            Point(4F, referenceWeight),
//            Point(5F, referenceWeight),
//            Point(6F, referenceWeight),
//            Point(7F, referenceWeight),
//            Point(8F, referenceWeight),
//        ),
//        lineStyle = LineStyle(
//            lineType = LineType.Straight(isDotted = false), // Solid line
//            color = MaterialTheme.colorScheme.secondary, // Styling color
//            width = 3f, // Line width
//            alpha = 0.8f // Transparency
//        )
//    )
//
//
//    // Including the horizontal line in the linePlotData
//    val lineChartProperties = LineChartProperties(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                horizontalLine // Add the reference line to the chart data
//            )
//        ),
//        xAxisProperties = xAxisProperties,
//        yAxisProperties = yAxisProperties,
//        gridLines = GridLinesUtil(
//            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f) // Softer grid lines
//        )
//    )
//
//    // Display the chart
//    LineChart(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(420.dp) // Slightly taller for spacing
//            .padding(horizontal = 16.dp), // Adds spacing from edges
//        lineChartProperties = lineChartProperties
//    )
//}
