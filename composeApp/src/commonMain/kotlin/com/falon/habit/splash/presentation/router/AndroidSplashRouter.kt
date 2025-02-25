package com.falon.habit.splash.presentation.router

class AndroidSplashRouter(
    private val routeToHabitsScreen: () -> Unit,
    private val loginActivityLauncher: () -> Unit,
) : SplashRouter {

    override fun routeToSignIn() {
        loginActivityLauncher()
    }

    override fun routeToHabitsScreen() {
        routeToHabitsScreen()
    }
}
