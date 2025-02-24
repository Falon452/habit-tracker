package com.falon.habit.splash.router

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
