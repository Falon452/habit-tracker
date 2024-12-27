package com.falon.habit.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.falon.habit.presentation.splash.effect.SplashEffect
import com.falon.habit.presentation.splash.router.SplashRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AndroidSplashViewModel : ViewModel() {

    private val _effects = MutableStateFlow<List<SplashEffect>>(emptyList())
    val effects = _effects.asStateFlow()

    init {
        _effects.sendEffect(SplashEffect.SignIn)
    }

    fun onEffect(
        effect: SplashEffect,
        router: SplashRouter,
    ) {
        when (effect) {
            SplashEffect.SignIn -> router.routeToSignIn()
        }
    }

    private fun <T> MutableStateFlow<List<T>>.sendEffect(effect: T) =
        update { prevEffects -> prevEffects.plus(effect) }
}