package com.falon.nosocialmedia.socialcounter.domain.model

import com.falon.nosocialmedia.socialcounter.domain.model.NotEmptyString.Companion.notEmptyStringOf
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.datetime.LocalTime
import kotlin.jvm.JvmInline

sealed class HabitCounter(
    open val id: UInt,
    open val numberOfDays: UInt,
    open val name: NotEmptyString,
    open val lastIncreaseTime: LocalTime,
) {

    private data class HabitCounterDataClass(
        override val id: UInt,
        override val numberOfDays: UInt,
        override val name: NotEmptyString,
        override val lastIncreaseTime: LocalTime,
    ) : HabitCounter(id, numberOfDays, name, lastIncreaseTime)


    companion object {

        fun of(id: Int, numberOfDays: Int, name: String, lastIncreaseTime: LocalTime): Result<HabitCounter, DomainError> {
            val notEmptyNameResult = name.notEmptyStringOf()

            if (id <= 0) {
                return Err(DomainError.RequireIdToBePositive)
            }
            if (numberOfDays <= 0) {
                return Err(DomainError.RequireNumberOfDaysToBePositive)
            }
            if (notEmptyNameResult.isErr) {
                return Err(DomainError.EmptyStringError)
            }

            return Ok(
                HabitCounterDataClass(
                    id = id.toUInt(),
                    numberOfDays = numberOfDays.toUInt(),
                    name = notEmptyNameResult.value,
                    lastIncreaseTime =
                )
            )
        }

        fun HabitCounter.getIncreasedCounter(): Result<HabitCounter, WasTodayUpdatedError> {
            if (wasTodayIncreased()) {
                return Err(WasTodayUpdatedError)
            }
            return Ok(
                (this as HabitCounterDataClass).copy(
                    id = id,
                    numberOfDays = numberOfDays.inc(),
                    name = name,
                )
            )
        }

        private fun HabitCounter.wasTodayIncreased(): Boolean {
            return lastIncreaseTime.
        }
    }
}

/**
 * All possible things that can happen in the use-cases
 */

object WasTodayUpdatedError
sealed class DomainError {

    object EmptyStringError : DomainError()
    object DatabaseIsAlreadyPopulated : DomainError()
    class DatabaseError(throwable: Throwable) : DomainError()
    object RequireIdToBePositive : DomainError()
    object RequireNumberOfDaysToBePositive : DomainError()
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