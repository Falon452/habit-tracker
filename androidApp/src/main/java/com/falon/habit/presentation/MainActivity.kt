package com.falon.habit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falon.habit.presentation.habit.ui.HabitsScreen
import com.falon.habit.presentation.splash.ui.SplashScreen
import com.falon.habit.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HabitsRoot()
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    AppTheme {
        HabitsRoot()
    }
}

@Composable
fun HabitsRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH_SCREEN,
    ) {
        composable(route = Routes.HABITS_SCREEN) {
            HabitsScreen()
        }
        composable(route = Routes.SPLASH_SCREEN) {
            SplashScreen(navController)
        }
    }
}
