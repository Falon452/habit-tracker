package com.falon.habit.domain.usecase

import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.domain.model.HabitCounter.Companion.INITIAL_COUNTER_VALUE
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class IsHabitDisabledUseCase {

    fun execute(habitCounter: HabitCounter): Boolean =
        habitCounter.wasTodayIncreased() &&
            habitCounter.notInitiallyCreated()

    private fun HabitCounter.notInitiallyCreated(): Boolean {
        return numberOfDays != INITIAL_COUNTER_VALUE.toUInt()
    }

    private fun HabitCounter.wasTodayIncreased(): Boolean {
        val nowLocalDateTime =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return lastIncreaseDateTime.date == nowLocalDateTime.date &&
            numberOfDays != INITIAL_COUNTER_VALUE.toUInt()
    }
}