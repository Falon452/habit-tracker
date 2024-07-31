package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.model.NoSocialCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository

class GetSocialMediaByIdUseCase(
    private val noSocialMediaRepository: NoSocialMediaRepository,
) {

    fun execute(id: Int): NoSocialCounter? {
        return noSocialMediaRepository.getSocialMedia(id)
    }
}