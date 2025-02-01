package com.falon.habit.domain.calculations

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.*

class StreakCalculationsTest {

    private val fixedTimeZone: TimeZone = TimeZone.UTC

    private fun localDate(year: Int, month: Int, day: Int): LocalDate =
        LocalDate(year, month, day)

    private fun toEpochMillis(date: LocalDate): Long =
        date.atTime(LocalTime(0, 0)).toInstant(fixedTimeZone).toEpochMilliseconds()

    @Test
    fun `WHEN timestamp list is empty THEN returns 0-day streak`() {
        val timestamps = emptyList<Long>()
        val result = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, fixedTimeZone)
        assertEquals(0, result)
    }

    @Test
    fun `WHEN a single timestamp is provided THEN returns 1-day streak`() {
        val date = localDate(2024, 2, 1)
        val epochMillis = toEpochMillis(date)
        val result = StreakCalculations.calculateNumberOfConsecutiveDays(listOf(epochMillis), fixedTimeZone)
        assertEquals(1, result)
    }

    @Test
    fun `WHEN timestamps represent consecutive days THEN returns correct streak length`() {
        val dates = listOf(
            localDate(2024, 2, 1),
            localDate(2024, 2, 2),
            localDate(2024, 2, 3)
        )
        val timestamps = dates.map { toEpochMillis(it) }
        val result = StreakCalculations.calculateNumberOfConsecutiveDays(timestamps, fixedTimeZone)
        assertEquals(3, result)
    }
}
