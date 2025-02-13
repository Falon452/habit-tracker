package com.falon.habit.presentation.model

import com.falon.habit.domain.model.NotEmptyString

data class HabitItem(
    val id: String,
    val name: NotEmptyString,
    val numberOfDays: Int,
    val isEnabled: Boolean,
)
