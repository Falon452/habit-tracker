package com.falon.nosocialmedia.socialcounter.presentation.factory

import com.falon.nosocialmedia.socialcounter.presentation.state.NoSocialMediasState

class NoSocialMediasStateFactory {

    fun create(): NoSocialMediasState =
        NoSocialMediasState(
            noSocialsCounter = listOf(
            ),
            isBottomDialogVisible = false,
            bottomDialogText = "",
        )
}
