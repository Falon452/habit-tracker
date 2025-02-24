package com.falon.habit.habits.presentation.model

import com.falon.habit.habits.domain.model.Habit

data class HabitsState(
    val habits: List<Habit> = listOf(),
    val isBottomDialogVisible: Boolean = false,
    val bottomDialogText: String = "",
    val isShareHabitDialogVisible: Boolean = false,
    val shareHabitId: String? = null,
)
