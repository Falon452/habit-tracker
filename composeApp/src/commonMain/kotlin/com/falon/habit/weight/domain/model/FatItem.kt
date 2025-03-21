package com.falon.habit.weight.domain.model

import kotlinx.datetime.LocalDateTime

data class FatItem(
    val value: Float,
    val time: LocalDateTime,
)
