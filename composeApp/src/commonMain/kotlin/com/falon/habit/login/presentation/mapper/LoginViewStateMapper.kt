package com.falon.habit.login.presentation.mapper

class LoginViewStateMapper {
    fun from(state: LoginState): LoginViewState = with(state) {
        LoginViewState(
            email = email,
            password = password,
            isLoading = isLoading,
            isAuthenticated = isAuthenticated,
            errorMessage = errorMessage
        )
    }
}
