package com.falon.habit.habits.domain.calculations

import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

object StreakCalculations {

    fun calculateNumberOfConsecutiveDays(
        timestamps: List<Long>,
        today: LocalDate,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Int {
        if (timestamps.isEmpty()) return 0

        val dates: List<LocalDate> = timestamps
            .map { Instant.fromEpochMilliseconds(it).toLocalDateTime(timeZone).date }

        var currentStreak = 1

        for (i in 1 until dates.size) {
            if (dates[i] == dates[i - 1].plus(1, DAY)) {
                currentStreak++
            } else {
                currentStreak = 1
            }
        }

        // 19.03  17.03 or 16.03 or 15.03 ... then return 0
        if (today.minus(1, DAY) > dates.last()) {
            return 0
        }

        return currentStreak
    }
}
