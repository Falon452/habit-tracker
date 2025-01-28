package com.falon.habit.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val uid: String,
)
