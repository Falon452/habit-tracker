package com.falon.habit.register.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.falon.habit.Routes
import com.falon.habit.register.presentation.mapper.RegisterState
import com.falon.habit.register.presentation.mapper.RegisterViewState
import com.falon.habit.register.presentation.model.RegisterEvent
import com.falon.habit.register.presentation.mapper.RegisterViewStateMapper
import dev.gitlive.firebase.auth.FirebaseAuth
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

class RegisterViewModel(
    private val auth: FirebaseAuth,
    private val viewStateMapper: RegisterViewStateMapper
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(RegisterState())
    val viewState: StateFlow<RegisterViewState> = _state
        .map(viewStateMapper::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(0),
            viewStateMapper.from(_state.value)
        )
    private val _events = Channel<RegisterEvent>()
    val events: Flow<RegisterEvent> = _events.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onRegister() {
        val currentState = _state.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.update { it.copy(errorMessage = "Email and password cannot be empty") }
            return
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { auth.createUserWithEmailAndPassword(currentState.email, currentState.password) }
                .map { result ->
                    _state.update { it.copy(isAuthenticated = true, isLoading = false) }
                    _events.send(RegisterEvent.NavigateToHabits)
                }
                .onFailure { err ->
                    _state.update { it.copy(isLoading = false, errorMessage = err.message) }
                }
        }
    }

    fun onNavigateToLogin() {

    }

    fun onEvent(
        event: RegisterEvent,
        navController: NavController,
    ) {
        when (event) {
            RegisterEvent.NavigateToHabits -> navController.navigate(Routes.HabitsScreen) {
                popUpTo(Routes.RegisterScreen) { inclusive = true }
            }
        }
    }
}
