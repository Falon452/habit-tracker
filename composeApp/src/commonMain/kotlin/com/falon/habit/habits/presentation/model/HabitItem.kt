package com.falon.habit.habits.presentation.model

data class HabitItem(
    val id: String,
    val name: String,
    val numberOfDays: Int,
    val isEnabled: Boolean,
)
