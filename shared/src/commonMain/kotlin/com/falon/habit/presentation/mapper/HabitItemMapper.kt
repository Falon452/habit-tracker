package com.falon.habit.presentation.mapper

import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.domain.usecase.IsHabitDisabledUseCase
import com.falon.habit.presentation.model.HabitItem

class HabitItemMapper(
    private val isHabitDisabledUseCase: IsHabitDisabledUseCase,
) {

    fun from(habitCounter: HabitCounter): HabitItem =
        with(habitCounter) {
            HabitItem(
                id = id,
                name = name,
                numberOfDays = numberOfDays,
                isEnabled = !isHabitDisabledUseCase.execute(habitCounter),
            )
        }
}