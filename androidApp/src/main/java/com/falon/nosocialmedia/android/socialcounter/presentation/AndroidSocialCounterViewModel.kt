package com.falon.nosocialmedia.android.socialcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.nosocialmedia.socialcounter.domain.interactor.IncreaseNoMediaCounterUseCase
import com.falon.nosocialmedia.socialcounter.domain.interactor.PopulateDatabaseUseCase
import com.falon.nosocialmedia.socialcounter.presentation.factory.NoSocialMediasStateFactory
import com.falon.nosocialmedia.socialcounter.presentation.mapper.NoSocialMediasViewStateMapper
import com.falon.nosocialmedia.socialcounter.presentation.viewmodel.NoSocialMediasViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSocialCounterViewModel @Inject constructor(
    noSocialMediasStateFactory: NoSocialMediasStateFactory,
    viewStateMapper: NoSocialMediasViewStateMapper,
    increaseNoMediaCounterUseCase: IncreaseNoMediaCounterUseCase,
    populateDatabaseUseCase: PopulateDatabaseUseCase,
): ViewModel() {

    private val viewModel by lazy {
        NoSocialMediasViewModel(
            viewStateFactory = noSocialMediasStateFactory,
            coroutineScope = viewModelScope,
            viewStateMapper = viewStateMapper,
            increaseNoMediaCounterUseCase = increaseNoMediaCounterUseCase,
            populateDatabaseUseCase = populateDatabaseUseCase,
        )
    }

    val state = viewModel.viewState

    fun onSocialMediaClicked(id: Int) {
        viewModel.onSocialMediaClicked(id)
    }
}
