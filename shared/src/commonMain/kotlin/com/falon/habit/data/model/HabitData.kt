package com.falon.habit.data.model

import com.falon.habit.domain.model.NotEmptyString
import kotlinx.serialization.Serializable

@Serializable
data class HabitData(
    val id: String,
    val userUid: String,
    val sharedWithUids: List<String>,
    val name: NotEmptyString,
    val streakTimestamps: List<Long>,
)