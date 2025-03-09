package com.falon.habit.register.presentation.mapper

class RegisterViewStateMapper {
    fun from(state: RegisterState): RegisterViewState = with(state) {
        RegisterViewState(
            email = email,
            password = password,
            isLoading = isLoading,
            isAuthenticated = isAuthenticated,
            errorMessage = errorMessage
        )
    }
}
