package com.falon.nosocialmedia.android.socialcounter.presentation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.nosocialmedia.socialcounter.data.NoSocialMediasRepository
import com.falon.nosocialmedia.socialcounter.domain.IncreaseNoMediaCounterUseCase
import com.falon.nosocialmedia.socialcounter.presentation.model.HabitsEffect
import com.falon.nosocialmedia.socialcounter.presentation.model.KeyboardController
import com.falon.nosocialmedia.socialcounter.presentation.viewmodel.NoSocialMediasState
import com.falon.nosocialmedia.socialcounter.presentation.viewmodel.NoSocialMediasViewModel
import com.falon.nosocialmedia.utils.CommonStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSocialCounterViewModel @Inject constructor(
    increaseNoMediaCounterUseCase: IncreaseNoMediaCounterUseCase,
    repository: NoSocialMediasRepository,
) : ViewModel() {

    private val viewModel by lazy {
        NoSocialMediasViewModel(
            coroutineScope = viewModelScope,
            increaseNoMediaCounterUseCase = increaseNoMediaCounterUseCase,
            repository = repository,
        )
    }

    val state: CommonStateFlow<NoSocialMediasState> = viewModel.viewState
    val effects = viewModel.effects

    fun onSocialMediaClicked(id: UInt) {
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
        focusRequester: FocusRequester
    ) {
        val commonKeyboardController = object : KeyboardController {
            override fun show() {
                focusRequester.requestFocus()
            }

            override fun hide() {
                keyboardController?.hide()
            }

        }
        viewModel.onEffect(effect, commonKeyboardController)
    }
}
