package com.falon.habit.weight.domain.model

import kotlinx.datetime.LocalDateTime

data class WeightItem(
    val value: Float,
    val time: LocalDateTime,
)
