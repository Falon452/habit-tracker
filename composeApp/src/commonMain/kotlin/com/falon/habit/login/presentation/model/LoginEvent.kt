package com.falon.habit.login.presentation.model

sealed interface LoginEvent {

    data object NavigateToMainScreen : LoginEvent
    data object NavigateToRegister : LoginEvent
}