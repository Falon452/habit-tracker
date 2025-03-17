package com.falon.habit.habits.domain.model

import kotlinx.datetime.LocalDateTime

data class Habit(
    val id: String,
    val userUid: String,
    val sharedWithUids: List<String>,
    val numberOfDays: Int,
    val name: String,
    val streakDateTimes: List<LocalDateTime>,
    val isDisabled: Boolean,
)


sealed interface DomainError {

    data class DatabaseError(val msg: String) : DomainError
}
