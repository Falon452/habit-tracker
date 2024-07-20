package com.falon.nosocialmedia.socialcounter.presentation.viewmodel

import com.falon.nosocialmedia.core.domain.flow.toCommonStateFlow
import com.falon.nosocialmedia.socialcounter.presentation.factory.NoSocialMediasStateFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoSocialMediasViewModel(
    private val viewStateFactory: NoSocialMediasStateFactory,
    private val coroutineScope: CoroutineScope?,
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(viewStateFactory.create())
    val state = _state.asStateFlow().toCommonStateFlow()

    fun onSocialMediaClicked(id: Int) {

    }
}
