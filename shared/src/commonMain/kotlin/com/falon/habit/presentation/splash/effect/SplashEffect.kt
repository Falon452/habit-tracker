package com.falon.habit.presentation.splash.effect

sealed interface SplashEffect {

    data object SignIn: SplashEffect
    data object RouteToMain : SplashEffect

    data class ScaleImage(val millis: Long): SplashEffect
    data class AlphaImage(val millis: Long): SplashEffect
}