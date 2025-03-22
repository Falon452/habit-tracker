package com.falon.habit.weight.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.falon.habit.rememberMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.multiplatform.cartesian.Scroll
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.Axis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.multiplatform.common.fill
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char


@Composable
fun Chart(
    modifier: Modifier = Modifier,
    x: List<Number>,
    y: List<Number>,
    goal: Number?,
    minY: Double? = null,
    maxY: Double? = 100.0,
    verticalAxisSuffix: String = "",
    markerSuffix: String = "",
) {
    val rangeProvider =
        remember(minY, maxY) { CartesianLayerRangeProvider.fixed(minY = minY, maxY = maxY) }
    val startAxisValueFormatter =
        remember { CartesianValueFormatter.decimal(suffix = verticalAxisSuffix) }
    val markerValueFormatter =
        remember { DefaultCartesianMarker.ValueFormatter.default(suffix = markerSuffix) }
    val bottomAxisValueFormatter = remember {
        object : CartesianValueFormatter {
            private val dateTimeFormat = LocalDate.Format {
                dayOfMonth()
                char('.')
                monthNumber()
                char('.')
                year()
            }

            override fun format(
                context: CartesianMeasuringContext,
                value: Double,
                verticalAxisPosition: Axis.Position.Vertical?
            ): String {
                val date = LocalDate.fromEpochDays(value.toInt())
                return dateTimeFormat.format(date)
            }
        }
    }

    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(x, y, goal, minY, minY) {
        modelProducer.runTransaction {
            if (x.isNotEmpty()) {
                lineSeries {
                    series(x, y)
                }
            }
            goal?.let {
                lineSeries {
                    if (x.isNotEmpty()) {
                        series(listOf(x.first(), x.last()), listOf(goal, goal))
                    } else {
                        series(listOf(1.0, 2.0), listOf(goal, goal))
                    }
                }
            }
        }
    }
    val lineColor = Color(0xffa485e0)
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                        areaFill =
                        LineCartesianLayer.AreaFill.single(
                            fill(
                                Brush.verticalGradient(
                                    listOf(
                                        lineColor.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                )
                            )
                        ),
                    )
                ),
                rangeProvider = rangeProvider,
            ),
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        stroke = LineCartesianLayer.LineStroke.Dashed()
                    )
                ),
                rangeProvider = rangeProvider,
            ),
            startAxis = VerticalAxis.rememberStart(valueFormatter = startAxisValueFormatter),
            bottomAxis = HorizontalAxis.rememberBottom(
                guideline = null,
                valueFormatter = bottomAxisValueFormatter
            ),
            marker = rememberMarker(markerValueFormatter),
        ),
        modelProducer = modelProducer,
        modifier = modifier.height(216.dp),
        scrollState = rememberVicoScrollState(scrollEnabled = true, Scroll.Absolute.End),
    )
}
