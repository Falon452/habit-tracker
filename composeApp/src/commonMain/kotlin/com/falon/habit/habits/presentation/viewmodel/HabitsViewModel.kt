package com.falon.habit.habits.presentation.viewmodel

import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.habit.habits.domain.usecase.CreateHabitUseCase
import com.falon.habit.habits.domain.usecase.IncreaseHabitStreakUseCase
import com.falon.habit.habits.domain.usecase.ObserveHabitsUseCase
import com.falon.habit.habits.domain.usecase.ShareHabitWithUseCase
import com.falon.habit.habits.domain.usecase.ShareHabitWithUseCaseResult
import com.falon.habit.habits.presentation.mapper.HabitsViewStateMapper
import com.falon.habit.habits.presentation.model.HabitsEffect
import com.falon.habit.habits.presentation.model.HabitsState
import com.falon.habit.habits.presentation.model.HabitsViewState
import com.github.michaelbull.result.filterErrors
import com.github.michaelbull.result.filterValues
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitsViewModel(
    private val increaseHabitStreakUseCase: IncreaseHabitStreakUseCase,
    private val shareHabitWithUseCase: ShareHabitWithUseCase,
    observeHabitsUseCase: ObserveHabitsUseCase,
    private val createHabitUseCase: CreateHabitUseCase,
    viewStateMapper: HabitsViewStateMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(HabitsState())
    val viewState: StateFlow<HabitsViewState> = _state
        .map(viewStateMapper::from)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = viewStateMapper.from(_state.value)
        )

    private val _effects = MutableStateFlow<List<HabitsEffect>>(emptyList())
    val effects = _effects.filter { it.isNotEmpty() }

    init {
        observeHabitsUseCase.execute()
            .onEach { habits ->
                _state.value = _state.value.copy(habits = habits.filterValues())

                habits.filterErrors().onEach {
                    println("Got error $it")
                }
            }
            .catch { e ->
                println("Error observing social media use case: ${e.message}")
            }
            .launchIn(viewModelScope)
    }

    fun onHabitClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            increaseHabitStreakUseCase.execute(id)
                .onSuccess {
                    println("SHOW TOAST TODO")
                }
                .onFailure {
                    _effects.sendEffect(HabitsEffect.ShowToast("Can't increase yet"))
                }
        }
    }

    fun onFabClick() {
        _state.update { it.copy(isBottomDialogVisible = true) }
        viewModelScope.launch(Dispatchers.Default) {
            delay(150)
            _effects.sendEffect(HabitsEffect.RequestFocusOnNewHabit)
        }
    }

    fun onDismissBottomSheetDialog() {
        _state.update {
            it.copy(
                isBottomDialogVisible = false,
            )
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val habitName = _state.value.bottomDialogText
            _state.update { it.copy(bottomDialogText = "") }
            _effects.sendEffect(HabitsEffect.HideKeyboard)
            createHabitUseCase.execute(habitName)
                .onSuccess {
                    _effects.sendEffect(HabitsEffect.ShowToast("Success"))
                }
                .onFailure {
                    _effects.sendEffect(HabitsEffect.ShowToast(it.msg))
                }
        }
    }

    fun onNewHabitTextChanged(text: String) {
        _state.update { it.copy(bottomDialogText = text) }
    }

    fun onEffect(
        effect: HabitsEffect,
        keyboardWithFocusOnNewHabit: SoftwareKeyboardController?,
        showToast: (String) -> Unit,
    ) {
        when (effect) {
            HabitsEffect.RequestFocusOnNewHabit -> keyboardWithFocusOnNewHabit?.show()
            HabitsEffect.HideKeyboard -> keyboardWithFocusOnNewHabit?.hide()
            is HabitsEffect.ShowToast -> showToast(effect.text)
        }
    }

    fun onShareHabitWith(email: String) {
        viewModelScope.launch {
            shareHabitWithUseCase.execute(email, requireNotNull(_state.value.shareHabitId))
                .onSuccess {
                    _state.value = _state.value.copy(
                        isShareHabitDialogVisible = false,
                        shareHabitId = null,
                    )
                }
                .onFailure { result ->
                    when (result) {
                        is ShareHabitWithUseCaseResult.NoUserWithEmailFound ->
                            _effects.sendEffect(HabitsEffect.ShowToast("No User with email: ${result.email}"))
                    }
                }
        }
    }

    fun onHabitLongClicked(habitId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(
                    isShareHabitDialogVisible = true,
                    shareHabitId = habitId,
                )
            }
        }
    }

    fun onShareHabitDialogDismiss() {
        viewModelScope.launch(Dispatchers.Default) {
            _state.update {
                it.copy(
                    isShareHabitDialogVisible = false,
                    shareHabitId = null,
                )
            }
        }
    }

    fun consumeEffect(): HabitsEffect =
        _effects.getAndUpdate { _effects.value.drop(1) }.first()

    private fun <T> MutableStateFlow<List<T>>.sendEffect(effect: T) {
        update { it + effect }
    }
}
