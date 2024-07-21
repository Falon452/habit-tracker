package com.falon.nosocialmedia.android.socialcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falon.nosocialmedia.socialcounter.presentation.factory.NoSocialMediasStateFactory
import com.falon.nosocialmedia.socialcounter.presentation.mapper.NoSocialMediasViewStateMapper
import com.falon.nosocialmedia.socialcounter.presentation.viewmodel.NoSocialMediasViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSocialCounterViewModel @Inject constructor(): ViewModel() {

    private val viewModel by lazy {
        NoSocialMediasViewModel(
            viewStateFactory = NoSocialMediasStateFactory(),
            coroutineScope = viewModelScope,
            viewStateMapper = NoSocialMediasViewStateMapper()
        )
    }

    val state = viewModel.viewState

    fun onSocialMediaClicked(id: Int) {
        viewModel.onSocialMediaClicked(id)
    }
}
