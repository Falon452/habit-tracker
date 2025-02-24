package com.falon.habit.habits.domain.specification

import kotlinx.datetime.*

class HabitDisabledSpec(
    private val clock: Clock = Clock.System,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {

    fun isMetBy(increaseUtcTimestamps: List<Long>): Boolean {
        if (increaseUtcTimestamps.isEmpty()) return false

        val latestIncreaseUtc = increaseUtcTimestamps.lastOrNull() ?: return false

        val latestIncreaseDateTime = Instant.fromEpochMilliseconds(latestIncreaseUtc)
            .toLocalDateTime(timeZone)

        val currentDate = clock.now().toLocalDateTime(timeZone).date

        return latestIncreaseDateTime.date == currentDate
    }
}
