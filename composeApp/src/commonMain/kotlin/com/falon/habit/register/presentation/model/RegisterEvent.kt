package com.falon.habit.register.presentation.model

sealed interface RegisterEvent {

    data object NavigateToHabits : RegisterEvent
}