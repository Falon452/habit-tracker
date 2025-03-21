package com.falon.habit.weight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.habit.weight.domain.model.WeightHistory
import com.falon.habit.weight.domain.usecase.InsertWeightUseCase
import com.falon.habit.weight.domain.usecase.ObserveWeightUseCase
import com.falon.habit.weight.presentation.effects.WeightHistoryEvents
import com.falon.habit.weight.presentation.effects.WeightHistoryEvents.ShowToast
import com.falon.habit.weight.presentation.mapper.WeightHistoryViewStateMapper
import com.falon.habit.weight.presentation.model.WeightHistoryState
import com.falon.habit.weight.presentation.model.WeightHistoryViewState
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeightHistoryViewModel(
    observeWeightHistoryUseCase: ObserveWeightUseCase,
    private val insertWeightHistoryUseCase: InsertWeightUseCase,
    viewStateMapper: WeightHistoryViewStateMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(WeightHistoryState())
    val viewState: StateFlow<WeightHistoryViewState> = _state
        .map(viewStateMapper::from)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = viewStateMapper.from(_state.value)
        )

    private val _events = Channel<WeightHistoryEvents>()
    val events: Flow<WeightHistoryEvents> = _events.receiveAsFlow()

    init {
        observeWeightHistoryUseCase.execute()
            .onEach { weightHistory ->
                weightHistory
                    .onSuccess { weightHistory ->
                        _state.update {
                            it.copy(weightHistory = weightHistory)
                        }
                    }
                    .onFailure {
                        when (it) {
                            is ObserveWeightUseCase.Companion.ObserveWeightError.SuccessButNoEntry -> {
                                _state.update {
                                    it.copy(
                                        weightHistory = WeightHistory(
                                            userUid = "will be set in InsertWeightUseCase",
                                        )
                                    )
                                }
                            }

                            is ObserveWeightUseCase.Companion.ObserveWeightError.NoUser -> {
                                _events.send(ShowToast(it.msg))
                            }

                            is ObserveWeightUseCase.Companion.ObserveWeightError.Unknown -> {
                                _events.send(ShowToast(it.msg))
                            }
                        }
                    }
            }
            .catch { e ->
                println("Error observing weight history: ${e.message}")
                _events.send(ShowToast("Failed to load weight history"))
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onWeightInputChanged(weight: String) {
        _state.update { it.copy(weight = weight.toFloatOrNull()) }
    }

    fun onFatInputChanged(fat: String) {
        _state.update { it.copy(fat = fat.toFloatOrNull()) }
    }

    fun onMuscleInputChanged(muscle: String) {
        _state.update { it.copy(muscle = muscle.toFloatOrNull()) }
    }

    fun onWaterInputChanged(water: String) {
        _state.update { it.copy(water = water.toFloatOrNull()) }
    }

    fun onBmiInputChanged(bmi: String) {
        _state.update { it.copy(bmi = bmi.toFloatOrNull()) }
    }

    fun onBonesInputChanged(bones: String) {
        _state.update { it.copy(bones = bones.toFloatOrNull()) }
    }

    fun onWeightGoalInputChanged(weightGoal: String) {
        _state.update { it.copy(weightGoal = weightGoal.toFloatOrNull()) }
    }

    fun onFatGoalInputChanged(fatGoal: String) {
        _state.update { it.copy(fatGoal = fatGoal.toFloatOrNull()) }
    }

    fun onMuscleGoalInputChanged(muscleGoal: String) {
        _state.update { it.copy(muscleGoal = muscleGoal.toFloatOrNull()) }
    }

    fun onWaterGoalInputChanged(waterGoal: String) {
        _state.update { it.copy(waterGoal = waterGoal.toFloatOrNull()) }
    }

    fun onBmiGoalInputChanged(bmiGoal: String) {
        _state.update { it.copy(bmiGoal = bmiGoal.toFloatOrNull()) }
    }

    fun onBonesGoalInputChanged(bonesGoal: String) {
        _state.update { it.copy(bonesGoal = bonesGoal.toFloatOrNull()) }
    }

    fun onSaveClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value

            insertWeightHistoryUseCase.execute(
                weight = state.weight,
                fat = state.fat,
                muscle = state.muscle,
                water = state.water,
                bones = state.bones,
                weightGoal = state.weightGoal,
                fatGoal = state.fatGoal,
                muscleGoal = state.muscleGoal,
                waterGoal = state.waterGoal,
                bmiGoal = state.bmiGoal,
                bonesGoal = state.bonesGoal,
                weightHistory = state.weightHistory,
            )
                .onSuccess {
                    _events.send(ShowToast("Data saved successfully"))
                    clearInputs()
                }
                .onFailure { error ->
                    _events.send(ShowToast("Failed to save data: ${error.msg}"))
                }
        }
    }

    private fun clearInputs() {
        _state.update {
            it.copy(
                weight = null,
                fat = null,
                muscle = null,
                water = null,
                bmi = null,
                bones = null,
            )
        }
    }

    fun onEvent(
        event: WeightHistoryEvents,
        showToast: (String) -> Unit,
    ) {
        when (event) {
            is ShowToast -> showToast(event.message)
        }
    }
}
