package com.falon.habit.habits.domain.model

import com.falon.habit.habits.domain.model.NotEmptyString.Companion.notEmptyStringOf
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

data class Habit(
    val id: String,
    val userUid: String,
    val sharedWithUids: List<String>,
    val numberOfDays: Int,
    val name: NotEmptyString,
    val streakDateTimes: List<LocalDateTime>,
    val isDisabled: Boolean,
) {

    companion object {

        private const val INITIAL_COUNTER_VALUE = 0

        private fun notInitiallyCreated(numberOfDays: Int): Boolean {
            return numberOfDays != INITIAL_COUNTER_VALUE
        }

        private fun wasTodayIncreased(
            numberOfDays: Int,
            lastIncreaseDateTime: LocalDateTime
        ): Boolean {
            val nowLocalDateTime =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return lastIncreaseDateTime.date == nowLocalDateTime.date &&
                    numberOfDays != INITIAL_COUNTER_VALUE
        }

        fun create(
            id: String,
            numberOfDays: Int,
            name: String,
            lastIncreaseTimestamp: Long
        ): Result<Habit, DomainError> {
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
            } catch (_: IllegalArgumentException) {
                return Err(DomainError.LocalDateTimeConversionError)
            }

            val numberOfDaysUInt = numberOfDays
            return Ok(
                Habit(
                    id = id,
                    numberOfDays = numberOfDaysUInt,
                    name = notEmptyNameResult.value,
                    streakDateTimes = listOf(dateTime),
                    userUid = requireNotNull(Firebase.auth.currentUser?.uid),
                    sharedWithUids = emptyList(),
                    isDisabled = notInitiallyCreated(numberOfDaysUInt) && wasTodayIncreased(
                        numberOfDaysUInt, dateTime
                    )
                )
            )
        }

        private fun generateRandomId(length: Int = 12): String {
            val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { charset.random() }
                .joinToString("")
        }

        fun create(bottomDialogText: String): Result<Habit, DomainError> {
            val notEmptyNameResult = bottomDialogText.notEmptyStringOf()

            if (notEmptyNameResult.isErr) {
                return Err(DomainError.EmptyStringError)
            }

            return Ok(
                Habit(
                    numberOfDays = INITIAL_COUNTER_VALUE,
                    name = notEmptyNameResult.value,
                    streakDateTimes = emptyList(),
                    id = generateRandomId(),
                    userUid = requireNotNull(Firebase.auth.currentUser?.uid),
                    sharedWithUids = emptyList(),
                    isDisabled = false,
                )
            )

        }
    }
}

sealed interface DomainError {

    data object EmptyStringError : DomainError

    data object DatabaseError : DomainError
    data object RequireIdNotToBeEmpty : DomainError
    data object RequireNumberOfDaysToBeNotNegative : DomainError
    data object LocalDateTimeConversionError : DomainError
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
