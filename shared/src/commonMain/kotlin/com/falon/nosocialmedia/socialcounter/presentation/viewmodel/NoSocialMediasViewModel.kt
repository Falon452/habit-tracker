package com.falon.nosocialmedia.socialcounter.presentation.viewmodel

import com.falon.nosocialmedia.core.domain.flow.toCommonFlow
import com.falon.nosocialmedia.core.domain.flow.toCommonStateFlow
import com.falon.nosocialmedia.socialcounter.presentation.factory.NoSocialMediasStateFactory
import com.falon.nosocialmedia.socialcounter.presentation.mapper.NoSocialMediasViewStateMapper
import com.falon.nosocialmedia.socialcounter.presentation.viewstate.NoSocialMediasViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NoSocialMediasViewModel(
    private val viewStateFactory: NoSocialMediasStateFactory,
    private val coroutineScope: CoroutineScope?,
    private val viewStateMapper: NoSocialMediasViewStateMapper
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(viewStateFactory.create())
    val viewState: StateFlow<NoSocialMediasViewState> = _state
        .map { state -> viewStateMapper.from(state) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = viewStateMapper.from(_state.value)
        )

    fun onSocialMediaClicked(id: Int) {

    }
}
