package com.falon.habit.presentation.splash.router

import androidx.navigation.NavController
import com.falon.habit.presentation.Routes

class AndroidSplashRouter(
    private val navController: NavController,
    private val loginActivityLauncher: () -> Unit,
) : SplashRouter {

    override fun routeToSignIn() {
        loginActivityLauncher()
    }

    override fun routeToHabitsScreen() {
        navController.navigate(Routes.HABITS_SCREEN)
    }
}
