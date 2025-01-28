package com.falon.habit.presentation.habit.viewmodel

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.habit.data.HabitsRepository
import com.falon.habit.domain.usecase.IncreaseNoMediaCounterUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCase
import com.falon.habit.presentation.model.HabitsEffect
import com.falon.habit.presentation.model.KeyboardController
import com.falon.habit.presentation.viewmodel.HabitsState
import com.falon.habit.presentation.viewmodel.HabitsViewModel
import com.falon.habit.utils.CommonStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidHabitsViewModel @Inject constructor(
    increaseNoMediaCounterUseCase: IncreaseNoMediaCounterUseCase,
    shareHabitWithUseCase: ShareHabitWithUseCase,
    repository: HabitsRepository,
) : ViewModel() {

    private val viewModel by lazy {
        HabitsViewModel(
            coroutineScope = viewModelScope,
            increaseNoMediaCounterUseCase = increaseNoMediaCounterUseCase,
            shareHabitWithUseCase = shareHabitWithUseCase,
            repository = repository,
        )
    }

    val state: CommonStateFlow<HabitsState> = viewModel.viewState
    val effects = viewModel.effects

    fun onHabitClicked(id: String) {
        viewModel.onSocialMediaClicked(id)
    }

    fun onFabClick() {
        viewModel.onFabClick()
    }

    fun onNewHabit() {
        viewModel.onNewHabit()
    }

    fun consumeEffect(): HabitsEffect {
        return viewModel.consumeEffect()
    }

    fun onNewHabitTextChanged(habitName: String) {
        viewModel.onNewHabitTextChanged(habitName)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onEffect(
        effect: HabitsEffect,
        keyboardController: SoftwareKeyboardController?,
        focusRequester: FocusRequester,
        showToast: (String) -> Unit,
    ) {
        val commonKeyboardController = object : KeyboardController {
            override fun show() {
                focusRequester.requestFocus()
            }

            override fun hide() {
                keyboardController?.hide()
            }

        }
        viewModel.onEffect(effect, commonKeyboardController, showToast)
    }

    fun onShareHabitWith(email: String) {
        viewModel.onShareHabitWith(email)
    }

    fun onHabitLongClicked(habitId: String) {
        viewModel.onHabitLongClicked(habitId)
    }

    fun onShareHabitDialogDismiss() {
        viewModel.onShareHabitDialogDismiss()
    }
}
