package com.falon.habit.presentation.viewmodel

import com.falon.habit.utils.toCommonFlow
import com.falon.habit.utils.toCommonStateFlow
import com.falon.habit.domain.model.Habit
import com.falon.habit.domain.usecase.AddHabitUseCase
import com.falon.habit.domain.usecase.IncreaseHabitStreakUseCase
import com.falon.habit.domain.usecase.ObserveHabitsUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCaseResult
import com.falon.habit.presentation.mapper.HabitsViewStateMapper
import com.falon.habit.presentation.model.HabitsEffect
import com.falon.habit.presentation.model.HabitsState
import com.falon.habit.presentation.model.HabitsViewState
import com.falon.habit.presentation.model.KeyboardController
import com.falon.habit.utils.CommonStateFlow
import com.github.michaelbull.result.filterErrors
import com.github.michaelbull.result.filterValues
import com.github.michaelbull.result.fold
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
    coroutineScope: CoroutineScope?,
    private val increaseHabitStreakUseCase: IncreaseHabitStreakUseCase,
    private val shareHabitWithUseCase: ShareHabitWithUseCase,
    observeHabitsUseCase: ObserveHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    viewStateMapper: HabitsViewStateMapper,
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableStateFlow(HabitsState())
    val viewState: CommonStateFlow<HabitsViewState> = _state
        .map(viewStateMapper::from)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = viewStateMapper.from(_state.value)
        )
        .toCommonStateFlow()

    private val _effects = MutableStateFlow<List<HabitsEffect>>(emptyList())
    val effects = _effects.filter { it.isNotEmpty() }.toCommonFlow()

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

    fun onSocialMediaClicked(id: String) {
        viewModelScope.launch {
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
        _state.value = _state.value.copy(
            isBottomDialogVisible = true
        )
        viewModelScope.launch(Dispatchers.Default) {
            delay(150)
            _effects.sendEffect(HabitsEffect.RequestFocusOnNewHabit)
        }
    }

    fun onNewHabit() {
        viewModelScope.launch {
            Habit.create(_state.value.bottomDialogText).fold(
                success = {
                    addHabitUseCase.execute(it)
                },
                failure = {
                    println(it)
                }
            )
            _state.value = _state.value.copy(
                bottomDialogText = "",
                isBottomDialogVisible = false,
            )
            _effects.sendEffect(HabitsEffect.HideKeyboard)
        }
    }

    fun onNewHabitTextChanged(text: String) {
        _state.value = _state.value.copy(
            bottomDialogText = text
        )
    }

    fun consumeEffect(): HabitsEffect =
        _effects.getAndUpdate { _effects.value.drop(1) }.first()

    fun onEffect(
        effect: HabitsEffect,
        keyboardWithFocusOnNewHabit: KeyboardController,
        showToast: (String) -> Unit,
    ) {
        when(effect) {
            HabitsEffect.RequestFocusOnNewHabit -> keyboardWithFocusOnNewHabit.show()
            HabitsEffect.HideKeyboard -> keyboardWithFocusOnNewHabit.hide()
            is HabitsEffect.ShowToast -> showToast(effect.text)
        }
    }

    private fun <T> MutableStateFlow<List<T>>.sendEffect(effect: T) {
        update { it + effect }
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
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isShareHabitDialogVisible = true,
                shareHabitId = habitId,
            )
        }
    }

    fun onShareHabitDialogDismiss() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isShareHabitDialogVisible = false,
                shareHabitId = null,
            )
        }
    }
}
