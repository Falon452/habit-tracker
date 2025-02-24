package com.falon.habit.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.habit.habits.domain.model.User
import com.falon.habit.habits.domain.usecase.RegisterUserUseCase
import com.falon.habit.splash.effect.SplashEffect
import com.falon.habit.splash.effect.SplashEffect.AlphaImage
import com.falon.habit.splash.effect.SplashEffect.ScaleImage
import com.falon.habit.splash.router.AndroidSplashRouter
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus


class SplashViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val _effects: MutableStateFlow<List<SplashEffect>> = MutableStateFlow(emptyList())
    val effects = _effects.asStateFlow()

    fun onSignInResult(isSigned: Boolean) {
        if (isSigned) {
            Firebase.auth.currentUser?.let { user ->
                val email = user.email
                if (email != null) {
                    viewModelScope.launch {
                        registerUserUseCase.execute(User(email = email, uid = user.uid))
                    }
                } else {
//                    Log.i(
//                        TAG,
//                        "Email is null for user $user"
//                    )
                }
            }

            _effects.sendEffect(SplashEffect.RouteToHabits)
        } else {
            _effects.sendEffect(SplashEffect.SignIn)
        }
    }

    fun onViewCreated() {
        viewModelScope.launch {
            _effects.sendEffect(
                AlphaImage(ANIMATION_DURATION_MILLIS),
                ScaleImage(ANIMATION_DURATION_MILLIS),
            )
            delay(SPLASH_DURATION_MILLIS)
            if (Firebase.auth.currentUser == null) {
                _effects.sendEffect(SplashEffect.SignIn)
            } else {
                _effects.sendEffect(SplashEffect.RouteToHabits)
            }
        }
    }

    fun onEffect(
        effect: SplashEffect,
        router: AndroidSplashRouter,
        alphaImage: (Long) -> Unit,
        scaleImage: (Long) -> Unit,
    ) {
        when (effect) {
            SplashEffect.SignIn -> router.routeToSignIn()
            is AlphaImage -> alphaImage(effect.millis)
            is ScaleImage -> scaleImage(effect.millis)
            SplashEffect.RouteToHabits -> router.routeToHabitsScreen()
        }
    }

    private fun <T> MutableStateFlow<List<T>>.sendEffect(vararg effects: T) =
        update { prevEffects -> prevEffects.plus(effects) }

    fun consumeEffect(): SplashEffect? =
        _effects.getAndUpdate { it.drop(1) }.firstOrNull()

    private companion object {

        const val TAG = "AndroidSplashViewModel"
        const val ANIMATION_DURATION_MILLIS = 1000L
        const val SPLASH_DURATION_MILLIS = 1200L
    }
}
