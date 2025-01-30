package com.falon.habit.presentation.model

import com.falon.habit.domain.model.HabitCounter

data class HabitsState(
    val habitCounters: List<HabitCounter> = listOf(),
    val isBottomDialogVisible: Boolean = false,
    val bottomDialogText: String = "",
    val isShareHabitDialogVisible: Boolean = false,
    val shareHabitId: String? = null,
)
