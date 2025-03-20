package com.falon.habit.login.presentation.mapper

data class LoginViewState(
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val isAuthenticated: Boolean,
    val errorMessage: String?
)
