package com.falon.habit.habits.presentation.mapper

import com.falon.habit.habits.presentation.model.HabitsState
import com.falon.habit.habits.presentation.model.HabitsViewState

class HabitsViewStateMapper(
    private val habitItemMapper: HabitItemMapper,
) {

    fun from(state: HabitsState) =
        with(state) {
            HabitsViewState(
                habits.map(habitItemMapper::from),
                isBottomDialogVisible,
                bottomDialogText,
                isShareHabitDialogVisible,
                shareHabitId,
            )
        }
}
