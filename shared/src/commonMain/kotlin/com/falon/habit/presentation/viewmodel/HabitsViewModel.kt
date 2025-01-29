package com.falon.habit.presentation.viewmodel

import com.falon.habit.data.HabitsRepository
import com.falon.habit.utils.toCommonFlow
import com.falon.habit.utils.toCommonStateFlow
import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.domain.usecase.IncreaseNoMediaCounterUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCaseResult
import com.falon.habit.presentation.model.HabitsEffect
import com.falon.habit.presentation.model.KeyboardController
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HabitsState(
    val habitCounters: List<HabitCounter> = listOf(),
    val isBottomDialogVisible: Boolean = false,
    val bottomDialogText: String = "",
    val isShareHabitDialogVisible: Boolean = false,
    val shareHabitId: String? = null,
)

class HabitsViewModel(
    coroutineScope: CoroutineScope?,
    private val increaseNoMediaCounterUseCase: IncreaseNoMediaCounterUseCase,
    private val shareHabitWithUseCase: ShareHabitWithUseCase,
    private val repository: HabitsRepository,
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableStateFlow(HabitsState())
    val viewState = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )
        .toCommonStateFlow()

    private val _effects = MutableStateFlow<List<HabitsEffect>>(emptyList())
    val effects = _effects.filter { it.isNotEmpty() }.toCommonFlow()

    init {
        repository.observeSocialMedias()
            .onEach { socialMedias ->
                _state.value = _state.value.copy(habitCounters = socialMedias.filterValues())

                socialMedias.filterErrors().onEach {
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
            increaseNoMediaCounterUseCase.execute(id)
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
            HabitCounter.firstCreation(_state.value.bottomDialogText).fold(
                success = {
                    repository.insertHabits(it)
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
