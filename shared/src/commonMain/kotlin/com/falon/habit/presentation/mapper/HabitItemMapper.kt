package com.falon.habit.presentation.mapper

import com.falon.habit.domain.model.Habit
import com.falon.habit.presentation.model.HabitItem

class HabitItemMapper {

    fun from(habit: Habit): HabitItem =
        with(habit) {
            HabitItem(
                id = id,
                name = name,
                numberOfDays = numberOfDays,
                isEnabled = !isDisabled,
            )
        }
}
