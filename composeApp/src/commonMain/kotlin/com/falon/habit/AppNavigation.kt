package com.falon.habit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falon.habit.habits.presentation.ui.AppTheme
import com.falon.habit.login.ui.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        AppTheme {
            NavHost(
                navController = navController,
                startDestination = Routes.SPLASH_SCREEN,
            ) {
                composable(route = Routes.HABITS_SCREEN) {
//            HabitsScreen()
                }
                composable(route = Routes.SPLASH_SCREEN) {
                    LoginScreen()
                }
            }
        }
    }
}
