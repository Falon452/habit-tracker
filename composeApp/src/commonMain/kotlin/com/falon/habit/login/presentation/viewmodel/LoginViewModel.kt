package com.falon.habit.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.falon.habit.Routes
import com.falon.habit.login.presentation.auth.GoogleAuthUiProvider
import com.falon.habit.login.presentation.mapper.LoginState
import com.falon.habit.login.presentation.mapper.LoginViewState
import com.falon.habit.login.presentation.mapper.LoginViewStateMapper
import com.falon.habit.login.presentation.model.LoginEvent
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val auth: FirebaseAuth,
    private val viewStateMapper: LoginViewStateMapper,
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(LoginState())
    val viewState: StateFlow<LoginViewState> = _state
        .map(viewStateMapper::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(0),
            viewStateMapper.from(_state.value)
        )
    private val _events = Channel<LoginEvent>()
    val events: Flow<LoginEvent> = _events.receiveAsFlow()

    fun onViewCreated(googleAuthUiProvider: GoogleAuthUiProvider) {
        onSignInClicked(googleAuthUiProvider)
    }

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
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { auth.signInWithEmailAndPassword(currentState.email, currentState.password) }
                .map { result ->
                    _state.update { it.copy(isAuthenticated = true, isLoading = false) }
                    _events.send(LoginEvent.NavigateToMainScreen)
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false, errorMessage = err.message) }
                }
        }
    }

    fun onSignInClicked(googleAuthUiProvider: GoogleAuthUiProvider) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val googleAccount = googleAuthUiProvider.signIn()
            if (googleAccount == null) {
                return@launch
            }

            runCatching {
                val credential = GoogleAuthProvider.credential(googleAccount.token, null)
                val result = auth.signInWithCredential(credential)
                result
            }
                .map { result: AuthResult ->
                    _state.update { it.copy(isAuthenticated = true, isLoading = false) }
                    _events.send(LoginEvent.NavigateToMainScreen)
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false, errorMessage = err.message) }
                }
        }
    }


    fun onForgotPassword() {

    }

    fun onCreateAnAccount() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(LoginEvent.NavigateToRegister)
        }
    }

    fun onEvent(
        event: LoginEvent,
        navController: NavController,
    ) {
        when (event) {
            LoginEvent.NavigateToMainScreen -> navController.navigate(Routes.HabitsScreen)  {
                popUpTo(Routes.RegisterScreen) { inclusive = true }
            }
            LoginEvent.NavigateToRegister -> navController.navigate(Routes.RegisterScreen)
        }
    }
}
