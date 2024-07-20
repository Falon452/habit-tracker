package com.falon.nosocialmedia.socialcounter.presentation.factory

import com.falon.nosocialmedia.socialcounter.domain.model.NoSocialCounter
import com.falon.nosocialmedia.socialcounter.presentation.state.NoSocialMediasState

class NoSocialMediasStateFactory {

    fun create(): NoSocialMediasState {
        return NoSocialMediasState(emptyList()) // TODO
    }
}
