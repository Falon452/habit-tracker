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
import com.falon.habit.habits.presentation.ui.HabitsScreen
import com.falon.habit.login.presentation.ui.LoginScreen
import com.falon.habit.register.presentation.ui.RegisterScreen
import com.falon.habit.splash.presentation.ui.SplashScreen
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
                        HabitsScreen()
                    }
                    composable(route = Routes.LoginScreen) {
                        LoginScreen(navController = navController)
                    }
                    composable(route = Routes.RegisterScreen) {
                        RegisterScreen(navController = navController)
                    }
                }
            }
        }
    }
}
