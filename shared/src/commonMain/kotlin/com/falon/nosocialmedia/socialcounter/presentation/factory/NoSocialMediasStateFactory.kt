package com.falon.nosocialmedia.socialcounter.presentation.factory

import com.falon.nosocialmedia.socialcounter.domain.model.NoSocialCounter
import com.falon.nosocialmedia.socialcounter.presentation.state.NoSocialMediasState

class NoSocialMediasStateFactory {

    fun create(): NoSocialMediasState =
        NoSocialMediasState(
            noSocialsCounter = listOf(
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
                NoSocialCounter(12, 1, "Instagram"),
            ),
        ) // TODO
}
