package com.falon.habit.login.presentation.auth

import com.falon.habit.login.presentation.model.GoogleAccount

expect class GoogleAuthUiProvider {
    suspend fun signIn(): GoogleAccount?
}
