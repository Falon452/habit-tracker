package com.falon.habit.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.habit.presentation.splash.effect.SplashEffect
import com.falon.habit.presentation.splash.effect.SplashEffect.AlphaImage
import com.falon.habit.presentation.splash.effect.SplashEffect.ScaleImage
import com.falon.habit.presentation.splash.router.SplashRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AndroidSplashViewModel : ViewModel() {

    private val _effects: MutableStateFlow<List<SplashEffect>> = MutableStateFlow(emptyList())
    val effects = _effects.asStateFlow()

    init {
        viewModelScope.launch {
            _effects.sendEffect(
                AlphaImage(ANIMATION_DURATION_MILLIS),
                ScaleImage(ANIMATION_DURATION_MILLIS),
            )
            delay(ANIMATION_DURATION_MILLIS)
            _effects.sendEffect(SplashEffect.SignIn)
        }
    }

    fun onEffect(
        effect: SplashEffect,
        router: SplashRouter,
        alphaImage: (Long) -> Unit,
        scaleImage: (Long) -> Unit,
    ) {
        when (effect) {
            SplashEffect.SignIn -> router.routeToSignIn()
            is AlphaImage -> alphaImage(effect.millis)
            is ScaleImage -> scaleImage(effect.millis)
        }
    }

    private fun <T> MutableStateFlow<List<T>>.sendEffect(vararg effects: T) =
        update { prevEffects -> prevEffects.plus(effects) }

    fun consumeEffect(): SplashEffect? =
        _effects.getAndUpdate { it.drop(1) }.firstOrNull()

    private companion object {

        const val ANIMATION_DURATION_MILLIS = 2000L
    }
}
