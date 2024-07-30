package com.falon.nosocialmedia.socialcounter.domain.repository

import com.falon.nosocialmedia.core.domain.flow.CommonFlow
import com.falon.nosocialmedia.socialcounter.domain.model.NoSocialCounter

interface NoSocialMediaRepository {

    fun observeSocialMedias(): CommonFlow<List<NoSocialCounter>>

    fun insertSocialMedias(noSocialCounter: NoSocialCounter)

    fun getSocialMedia(id: Int): NoSocialCounter
}