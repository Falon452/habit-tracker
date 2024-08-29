package com.falon.nosocialmedia.socialcounter.presentation.viewstate

import com.falon.nosocialmedia.socialcounter.presentation.model.SocialMediaItem

data class NoSocialMediasViewState(
    val items: List<SocialMediaItem>,
    val isBottomDialogVisible: Boolean,
    val bottomDialogText: String,
)
