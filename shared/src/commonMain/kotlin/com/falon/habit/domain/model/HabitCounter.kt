package com.falon.habit.domain.model

import com.falon.habit.domain.model.NotEmptyString.Companion.notEmptyStringOf
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class HabitCounter(
    val id: String,
    val userUid: String,
    val sharedWithUids: List<String>,
    val numberOfDays: UInt,
    val name: NotEmptyString,
    val lastIncreaseDateTime: LocalDateTime,
) {

    companion object {

        private const val INITIAL_COUNTER_VALUE = 0

        fun firstCreation(
            id: String,
            numberOfDays: Int,
            name: String,
            lastIncreaseTimestamp: Long
        ): Result<HabitCounter, DomainError> {
            val notEmptyNameResult = name.notEmptyStringOf()
            val dateTime: LocalDateTime

            if (id.isEmpty()) {
                return Err(DomainError.RequireIdNotToBeEmpty)
            }
            if (numberOfDays < 0) {
                return Err(DomainError.RequireNumberOfDaysToBeNotNegative)
            }
            if (notEmptyNameResult.isErr) {
                return Err(DomainError.EmptyStringError)
            }
            try {
                dateTime = Instant.fromEpochMilliseconds(lastIncreaseTimestamp)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            } catch (e: IllegalArgumentException) {
                return Err(DomainError.LocalDateTimeConversionError)
            }

            return Ok(
                HabitCounter(
                    id = id,
                    numberOfDays = numberOfDays.toUInt(),
                    name = notEmptyNameResult.value,
                    lastIncreaseDateTime = dateTime,
                    userUid = requireNotNull(Firebase.auth.currentUser?.uid),
                    sharedWithUids = emptyList(),
                )
            )
        }

        private fun generateRandomId(length: Int = 12): String {
            val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { charset.random() }
                .joinToString("")
        }

        fun HabitCounter.getIncreasedCounter(): Result<HabitCounter, DomainError.WasTodayUpdatedError> {
            if (wasTodayIncreased()) {
                return Err(DomainError.WasTodayUpdatedError)
            }
            return Ok(
                this.copy(
                    id = id,
                    numberOfDays = numberOfDays.inc(),
                    name = name,
                    lastIncreaseDateTime = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                )
            )
        }

        private fun HabitCounter.wasTodayIncreased(): Boolean {
            val nowLocalDateTime =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return lastIncreaseDateTime.date == nowLocalDateTime.date &&
                    numberOfDays != INITIAL_COUNTER_VALUE.toUInt()
        }

        fun firstCreation(bottomDialogText: String): Result<HabitCounter, DomainError> {
            val notEmptyNameResult = bottomDialogText.notEmptyStringOf()
            val dateTime: LocalDateTime

            if (notEmptyNameResult.isErr) {
                return Err(DomainError.EmptyStringError)
            }
            try {
                val lastIncreaseTimestamp = Clock.System.now().toEpochMilliseconds()
                dateTime = Instant.fromEpochMilliseconds(lastIncreaseTimestamp)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            } catch (e: IllegalArgumentException) {
                return Err(DomainError.LocalDateTimeConversionError)
            }

            return Ok(
                HabitCounter(
                    numberOfDays = INITIAL_COUNTER_VALUE.toUInt(),
                    name = notEmptyNameResult.value,
                    lastIncreaseDateTime = dateTime,
                    id = generateRandomId(),
                    userUid = requireNotNull(Firebase.auth.currentUser?.uid),
                    sharedWithUids = emptyList(),
                )
            )

        }
    }
}

sealed interface DomainError {

    data object EmptyStringError : DomainError
    data object DatabaseIsAlreadyPopulated : DomainError
    class DatabaseError(throwable: Throwable) : DomainError
    data object RequireIdNotToBeEmpty : DomainError
    data object RequireNumberOfDaysToBeNotNegative : DomainError
    data object LocalDateTimeConversionError : DomainError
    data object WasTodayUpdatedError : DomainError
}

@JvmInline
@Serializable
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