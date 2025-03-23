package com.falon.habit.weight.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MuscleItemData(
    val value: Float,
    val time: Long,
)
