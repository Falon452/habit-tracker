package com.falon.habit.splash.presentation.effect

sealed interface SplashEffect {

    data object SignIn : SplashEffect
    data object RouteToHabits : SplashEffect
}
