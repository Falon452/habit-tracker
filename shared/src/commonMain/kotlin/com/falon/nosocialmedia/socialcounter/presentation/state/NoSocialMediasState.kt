package com.falon.nosocialmedia.socialcounter.presentation.state

import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter

data class NoSocialMediasState(
    val noSocialsCounter: List<HabitCounter>,
    val isBottomDialogVisible: Boolean,
    val bottomDialogText: String,
)
