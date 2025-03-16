package com.falon.habit.login.presentation.model

data class GoogleAccount(
    val token: String,
    val displayName: String = "",
    val profileImageUrl: String? = null,
)
