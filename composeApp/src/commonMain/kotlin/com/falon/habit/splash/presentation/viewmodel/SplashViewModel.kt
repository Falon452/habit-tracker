package com.falon.habit.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.falon.habit.Routes
import com.falon.habit.splash.presentation.effect.SplashEffect
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _effects: MutableStateFlow<List<SplashEffect>> = MutableStateFlow(emptyList())
    val effects = _effects.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            if (Firebase.auth.currentUser == null) {
                _effects.sendEffect(SplashEffect.SignIn)
            } else {
                _effects.sendEffect(SplashEffect.RouteToHabits)
            }
        }
    }

    fun onEffect(
        effect: SplashEffect,
        navController: NavController,
    ) {
        when (effect) {
            SplashEffect.SignIn -> navController.navigate(Routes.LoginScreen) {
                popUpTo(Routes.SplashScreen) { inclusive = true }
            }
            SplashEffect.RouteToHabits -> navController.navigate(Routes.HabitsScreen) {
                popUpTo(Routes.SplashScreen) { inclusive = true }
            }
        }
    }

    private fun <T> MutableStateFlow<List<T>>.sendEffect(vararg effects: T) =
        update { prevEffects -> prevEffects.plus(effects) }

    fun consumeEffect(): SplashEffect? =
        _effects.getAndUpdate { it.drop(1) }.firstOrNull()
}
