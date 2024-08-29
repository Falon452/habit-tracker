package com.falon.nosocialmedia.socialcounter.domain.model

import com.falon.nosocialmedia.socialcounter.domain.model.NotEmptyString.Companion.notEmptyStringOf
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmInline

sealed class HabitCounter(
    open val id: UInt,
    open val numberOfDays: UInt,
    open val name: NotEmptyString,
    open val lastIncreaseDateTime: LocalDateTime,
) {

    private data class HabitCounterDataClass(
        override val id: UInt,
        override val numberOfDays: UInt,
        override val name: NotEmptyString,
        override val lastIncreaseDateTime: LocalDateTime,
    ) : HabitCounter(id, numberOfDays, name, lastIncreaseDateTime)


    companion object {

        const val INITIAL_COUNTER_VALUE = 0

        fun of(id: Int, numberOfDays: Int, name: String, lastIncreaseTimestamp: Long): Result<HabitCounter, DomainError> {
            val notEmptyNameResult = name.notEmptyStringOf()
            val dateTime: LocalDateTime

            if (id <= 0) {
                return Err(DomainError.RequireIdToBePositive)
            }
            if (numberOfDays < 0) {
                return Err(DomainError.RequireNumberOfDaysToBeNotNegative)
            }
            if (notEmptyNameResult.isErr) {
                return Err(DomainError.EmptyStringError)
            }
            try {
                dateTime = Instant.fromEpochMilliseconds(lastIncreaseTimestamp).toLocalDateTime(TimeZone.currentSystemDefault())
            } catch (e: IllegalArgumentException) {
                return Err(DomainError.LocalDateTimeConversionError)
            }

            return Ok(
                HabitCounterDataClass(
                    id = id.toUInt(),
                    numberOfDays = numberOfDays.toUInt(),
                    name = notEmptyNameResult.value,
                    lastIncreaseDateTime = dateTime,
                )
            )
        }

        fun HabitCounter.getIncreasedCounter(): Result<HabitCounter, DomainError.WasTodayUpdatedError> {
            if (wasTodayIncreased()) {
                return Err(DomainError.WasTodayUpdatedError)
            }
            return Ok(
                (this as HabitCounterDataClass).copy(
                    id = id,
                    numberOfDays = numberOfDays.inc(),
                    name = name,
                    lastIncreaseDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                )
            )
        }

        private fun HabitCounter.wasTodayIncreased(): Boolean {
            val nowLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return lastIncreaseDateTime.date == nowLocalDateTime.date &&
                    numberOfDays != INITIAL_COUNTER_VALUE.toUInt()
        }
    }
}

sealed interface DomainError {

    data object EmptyStringError : DomainError
    data object DatabaseIsAlreadyPopulated : DomainError
    class DatabaseError(throwable: Throwable) : DomainError
    data object RequireIdToBePositive : DomainError
    data object RequireNumberOfDaysToBeNotNegative : DomainError
    data object LocalDateTimeConversionError : DomainError
    data object WasTodayUpdatedError : DomainError
}

@JvmInline
value class NotEmptyString private constructor(val value: String) {

    companion object {

        fun String.notEmptyStringOf(): Result<NotEmptyString, DomainError.EmptyStringError> =
            if (isNotEmpty()) {
                Ok(NotEmptyString(this))
            } else {
                Err(DomainError.EmptyStringError)
            }
    }
}