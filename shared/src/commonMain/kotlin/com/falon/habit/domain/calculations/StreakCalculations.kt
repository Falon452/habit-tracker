package com.falon.habit.domain.calculations

import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

object StreakCalculations {

    fun calculateNumberOfConsecutiveDays(
        timestamps: List<Long>,
        timeZone: TimeZone = TimeZone.currentSystemDefault(),
    ): Int {
        if (timestamps.isEmpty()) return 0

        val dates = timestamps
            .map { Instant.fromEpochMilliseconds(it).toLocalDateTime(timeZone).date }

        var currentStreak = 1

        for (i in 1 until dates.size) {
            if (dates[i] == dates[i - 1].plus(1, DAY)) {
                currentStreak++
            } else {
                currentStreak = 1
            }
        }

        return currentStreak
    }
}
