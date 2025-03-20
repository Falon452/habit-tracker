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


sealed class DomainError(open val msg: String) {

    data class DatabaseError(override val msg: String) : DomainError(msg)
    data class BlankName(override val msg: String) : DomainError(msg)
}
