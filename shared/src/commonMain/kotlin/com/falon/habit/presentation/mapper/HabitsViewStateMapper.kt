package com.falon.habit.presentation.mapper

import com.falon.habit.presentation.model.HabitsState
import com.falon.habit.presentation.model.HabitsViewState

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