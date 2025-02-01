package com.falon.habit.presentation.model

import com.falon.habit.domain.model.Habit

data class HabitsState(
    val habits: List<Habit> = listOf(),
    val isBottomDialogVisible: Boolean = false,
    val bottomDialogText: String = "",
    val isShareHabitDialogVisible: Boolean = false,
    val shareHabitId: String? = null,
)
