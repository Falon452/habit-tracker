package com.falon.habit.habits.presentation.model

import com.falon.habit.habits.domain.model.NotEmptyString

data class HabitItem(
    val id: String,
    val name: NotEmptyString,
    val numberOfDays: Int,
    val isEnabled: Boolean,
)
