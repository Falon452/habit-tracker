package com.falon.habit.weight.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FatItemData(
    val value: Float,
    val time: Long,
)
