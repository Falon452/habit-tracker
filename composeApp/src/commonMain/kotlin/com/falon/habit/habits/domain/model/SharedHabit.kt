package com.falon.habit.habits.domain.model

import kotlinx.datetime.LocalDateTime

data class SharedHabit(
    val id: String,
    val ownerUserUid: String,
    val sharedWithUserUids: List<String>,
    val name: String,
    val numberOfDays: Int,
    val isDisabled: Boolean,
    val streakDateTimes: List<Pair<String, LocalDateTime>>,
)
