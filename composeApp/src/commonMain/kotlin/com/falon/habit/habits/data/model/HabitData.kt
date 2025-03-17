package com.falon.habit.habits.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HabitData(
    val id: String,
    val userUid: String,
    val sharedWithUids: List<String>,
    val name: String,
    val streakTimestamps: List<Long>,
)
