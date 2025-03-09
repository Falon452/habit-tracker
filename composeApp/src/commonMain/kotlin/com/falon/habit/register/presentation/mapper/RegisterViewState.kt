package com.falon.habit.register.presentation.mapper

data class RegisterViewState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val isAuthenticated: Boolean,
    val errorMessage: String?
)
