package com.falon.habit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.LayeredComponent
import com.patrykandpatrick.vico.multiplatform.common.component.ShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.TextComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import com.patrykandpatrick.vico.multiplatform.common.fill
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import com.patrykandpatrick.vico.multiplatform.common.shape.MarkerCorneredShape
import org.koin.compose.KoinContext

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
