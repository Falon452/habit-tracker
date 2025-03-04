package com.falon.habit.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.habit.login.presentation.mapper.LoginState
import com.falon.habit.login.presentation.mapper.LoginViewState
import com.falon.habit.login.presentation.mapper.LoginViewStateMapper
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val auth: FirebaseAuth,
    private val viewStateMapper: LoginViewStateMapper
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(LoginState())
    val viewState: StateFlow<LoginViewState> = _state
        .map(viewStateMapper::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(0),
            viewStateMapper.from(_state.value)
        )

    fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onLogin() {
        val currentState = _state.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.update { it.copy(errorMessage = "Email and password cannot be empty") }
            return
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = auth.signInWithEmailAndPassword(currentState.email, currentState.password)
            if (result.user == null) {
                _state.update { it.copy(isLoading = false, errorMessage = "") }
            } else {
                _state.update { it.copy(isAuthenticated = true, isLoading = false) }
            }
        }
    }

    fun onGoogleSignInClicked() {

    }

    fun onForgotPassword() {

    }

    fun onNavigateToRegister() {

    }
}
