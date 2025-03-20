package com.falon.habit.habits.data.mapper

import com.falon.habit.habits.data.model.HabitData
import com.falon.habit.habits.domain.calculations.StreakCalculations
import com.falon.habit.habits.domain.model.Habit
import com.falon.habit.habits.domain.specification.HabitDisabledSpec
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class HabitDataMapper(
    private val habitDisabledSpec: HabitDisabledSpec,
    private val timeZone: TimeZone,
) {

    fun from(habitData: HabitData): Habit = with(habitData) {
        Habit(
            id = id,
            userUid = userUid,
            sharedWithUids = sharedWithUids,
            numberOfDays = StreakCalculations.calculateNumberOfConsecutiveDays(
                streakTimestamps,
                Clock.System.now().toLocalDateTime(timeZone).date
            ),
            name = name,
            streakDateTimes = streakTimestamps.map {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(timeZone)
            },
            isDisabled = habitDisabledSpec.isMetBy(streakTimestamps),
        )
    }

    fun from(habit: Habit): HabitData = with(habit) {
        HabitData(
            id = id,
            userUid = userUid,
            sharedWithUids = sharedWithUids,
            name = name,
            streakTimestamps = streakDateTimes.map { it.toInstant(timeZone).toEpochMilliseconds() }
        )
    }
}
