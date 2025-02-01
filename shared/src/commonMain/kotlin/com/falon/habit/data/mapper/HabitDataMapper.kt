package com.falon.habit.data.mapper

import com.falon.habit.data.model.HabitData
import com.falon.habit.domain.calculations.StreakCalculations
import com.falon.habit.domain.model.Habit
import com.falon.habit.domain.specification.HabitDisabledSpec
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class HabitDataMapper(
    private val habitDisabledSpec: HabitDisabledSpec,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {

    fun from(habitData: HabitData): Habit = with(habitData) {
        Habit(
            id = id,
            userUid = userUid,
            sharedWithUids = sharedWithUids,
            numberOfDays = StreakCalculations.calculateNumberOfConsecutiveDays(streakTimestamps),
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
