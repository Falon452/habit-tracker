package com.falon.nosocialmedia.android.socialcounter.presentation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.nosocialmedia.core.domain.flow.CommonStateFlow
import com.falon.nosocialmedia.socialcounter.domain.interactor.IncreaseNoMediaCounterUseCase
import com.falon.nosocialmedia.socialcounter.domain.interactor.ObserveSocialMediaUseCase
import com.falon.nosocialmedia.socialcounter.domain.interactor.PopulateDatabaseUseCase
import com.falon.nosocialmedia.socialcounter.presentation.factory.NoSocialMediasStateFactory
import com.falon.nosocialmedia.socialcounter.presentation.mapper.NoSocialMediasViewStateMapper
import com.falon.nosocialmedia.socialcounter.presentation.model.HabitsEffect
import com.falon.nosocialmedia.socialcounter.presentation.model.KeyboardController
import com.falon.nosocialmedia.socialcounter.presentation.viewmodel.NoSocialMediasViewModel
import com.falon.nosocialmedia.socialcounter.presentation.viewstate.NoSocialMediasViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSocialCounterViewModel @Inject constructor(
    noSocialMediasStateFactory: NoSocialMediasStateFactory,
    viewStateMapper: NoSocialMediasViewStateMapper,
    increaseNoMediaCounterUseCase: IncreaseNoMediaCounterUseCase,
    populateDatabaseUseCase: PopulateDatabaseUseCase,
    observeSocialMediasUseCase: ObserveSocialMediaUseCase,
): ViewModel() {

    private val viewModel by lazy {
        NoSocialMediasViewModel(
            viewStateFactory = noSocialMediasStateFactory,
            coroutineScope = viewModelScope,
            viewStateMapper = viewStateMapper,
            increaseNoMediaCounterUseCase = increaseNoMediaCounterUseCase,
            populateDatabaseUseCase = populateDatabaseUseCase,
            observeSocialMediasUseCase = observeSocialMediasUseCase,
        )
    }

    val state: CommonStateFlow<NoSocialMediasViewState> = viewModel.viewState
    val effects = viewModel.effects

    fun onSocialMediaClicked(id: Int) {
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
