package com.falon.habit.domain.specification

import kotlin.test.*
import kotlinx.datetime.*

class HabitDisabledSpecTest {

    private val fixedTimeZone = TimeZone.of("Europe/Warsaw")

    private fun fixedClock(fixedInstant: Instant): Clock = object : Clock {
        override fun now(): Instant = fixedInstant
    }

    @Test
    fun `WHEN timestamps list is empty THEN returns false`() {
        val habitDisabledSpec =
            HabitDisabledSpec(fixedClock(Instant.parse("2024-02-01T12:00:00Z")), fixedTimeZone)

        val timestamps = emptyList<Long>()

        val result = habitDisabledSpec.isMetBy(timestamps)

        assertFalse(result, "The result should be false when the list of timestamps is empty.")
    }

    @Test
    fun `WHEN latest increase is not today THEN returns false`() {
        // First second of the day
        val nowLocalDate = LocalDate(2024, 2, 1)
        val nowLocalDateTime = LocalDateTime(nowLocalDate, LocalTime(0, 0, 0))
        val nowUtc = nowLocalDateTime.toInstant(fixedTimeZone)
        // Last second of previous day
        val yesterdayLocalDate = nowLocalDate.minus(DatePeriod(days = 1))
        val yesterdayEndLocal = LocalDateTime(yesterdayLocalDate, LocalTime(23, 59, 59))
        val yesterdayEndUtc = yesterdayEndLocal.toInstant(fixedTimeZone)

        val timestamps = listOf(yesterdayEndUtc.toEpochMilliseconds())

        val result = HabitDisabledSpec(fixedClock(nowUtc), fixedTimeZone)
            .isMetBy(timestamps)

        assertFalse(
            result,
            "The result should be false when the latest increase is not from today."
        )
    }

    @Test
    fun `WHEN latest increase is today THEN returns true`() {
        // Last second of the day
        val nowLocalDate = LocalDate(2024, 2, 1)
        val nowLocalDateTime = LocalDateTime(nowLocalDate, LocalTime(23, 59, 59))
        val nowUtc = nowLocalDateTime.toInstant(fixedTimeZone)
        // First second of the day
        val yesterdayEndLocal = LocalDateTime(nowLocalDate, LocalTime(0, 0, 0))
        val yesterdayEndUtc = yesterdayEndLocal.toInstant(fixedTimeZone)

        val someTimestamp =
            LocalDateTime(nowLocalDate.minus(DatePeriod(days = 2)), LocalTime(0, 0, 0)).toInstant(
                fixedTimeZone
            ).toEpochMilliseconds()
        val timestamps = listOf(
            someTimestamp,
            yesterdayEndUtc.toEpochMilliseconds(),
        )

        val result = HabitDisabledSpec(fixedClock(nowUtc), fixedTimeZone)
            .isMetBy(timestamps)

        assertTrue(result, "The result should be true when the latest increase is from today.")
    }
}
