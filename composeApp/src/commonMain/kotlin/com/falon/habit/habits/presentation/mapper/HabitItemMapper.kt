package com.falon.habit.habits.presentation.mapper

import com.falon.habit.habits.domain.model.Habit
import com.falon.habit.habits.presentation.model.HabitItem

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
