package com.falon.habit.splash.effect

sealed interface SplashEffect {

    data object SignIn : SplashEffect
    data object RouteToHabits : SplashEffect

    data class ScaleImage(val millis: Long) : SplashEffect
    data class AlphaImage(val millis: Long) : SplashEffect
}
