package com.falon.habit.habits.domain.calculations

import kotlinx.datetime.*
import kotlin.test.Test
import kotlin.test.assertEquals

class StreakCalculationsTest {

    @Test
    fun testConsecutiveDays_StreakContinues() {
        // Example: clicks on March 23, 2025, March 24, 2025, March 25, 2025
        val timestamps = listOf(
            Instant.parse("2025-03-23T12:00:00Z").toEpochMilliseconds(),
            Instant.parse("2025-03-24T12:00:00Z").toEpochMilliseconds(),
            Instant.parse("2025-03-25T12:00:00Z").toEpochMilliseconds()
        )

        val today = LocalDate(2025, 3, 25)
        val streak = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, today)

        assertEquals(3, streak) // There are 3 consecutive days of activity
    }

    @Test
    fun testConsecutiveDays_StreakResetsAfterOneDayGap() {
        // Example: clicks on March 23, 2025, March 25, 2025 (gap of 2 days)
        val timestamps = listOf(
            Instant.parse("2025-03-23T12:00:00Z").toEpochMilliseconds(),
            Instant.parse("2025-03-25T12:00:00Z").toEpochMilliseconds()
        )

        val today = LocalDate(2025, 3, 25)
        val streak = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, today)

        assertEquals(1, streak) // Only 1 day in the streak due to 2-day gap
    }

    @Test
    fun testConsecutiveDays_StreakResetsAfterMoreThanOneDayGap() {
        // Example: clicks on March 23, 2025, March 26, 2025 (gap of 3 days)
        val timestamps = listOf(
            Instant.parse("2025-03-23T12:00:00Z").toEpochMilliseconds(),
            Instant.parse("2025-03-26T12:00:00Z").toEpochMilliseconds()
        )

        val today = LocalDate(2025, 3, 26)
        val streak = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, today)

        assertEquals(1, streak) // Streak is reset after more than 1 day gap
    }

    @Test
    fun testConsecutiveDays_EmptyTimestamps() {
        // No timestamps means no streak
        val timestamps = emptyList<Long>()
        val today = LocalDate(2025, 3, 25)
        val streak = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, today)

        assertEquals(0, streak) // No streak with empty list of timestamps
    }

    @Test
    fun testConsecutiveDays_SingleClick() {
        // Only a single click on March 23, 2025
        val timestamps = listOf(Instant.parse("2025-03-23T12:00:00Z").toEpochMilliseconds())
        val today = LocalDate(2025, 3, 23)
        val streak = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, today)

        assertEquals(1, streak) // Only one click, streak of 1 day
    }

    @Test
    fun testConsecutiveDays_GapToPast() {
        // A click on March 23, 2025, and today is March 24, 2025, but with a gap in the streak
        val timestamps = listOf(Instant.parse("2025-03-23T12:00:00Z").toEpochMilliseconds())
        val today = LocalDate(2025, 3, 24)
        val streak = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, today)

        assertEquals(0, streak) // Streak is reset due to gap of more than 1 day
    }
}
