package com.falon.nosocialmedia.socialcounter.presentation.mapper

import com.falon.nosocialmedia.socialcounter.presentation.model.SocialMediaItem
import com.falon.nosocialmedia.socialcounter.presentation.state.NoSocialMediasState
import com.falon.nosocialmedia.socialcounter.presentation.viewstate.NoSocialMediasViewState

class NoSocialMediasViewStateMapper {

    fun from(state: NoSocialMediasState): NoSocialMediasViewState {
        return NoSocialMediasViewState(
            items = state.noSocialsCounter.map {
                SocialMediaItem(
                    id = it.id,
                    name = it.name,
                    count = it.numberOfDays,
                )
            }
        )
    }
}
