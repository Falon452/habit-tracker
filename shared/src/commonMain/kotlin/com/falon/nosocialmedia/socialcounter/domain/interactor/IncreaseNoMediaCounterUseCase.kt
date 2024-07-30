package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository

class IncreaseNoMediaCounterUseCase(
    private val getSocialMediaByIdUseCase: GetSocialMediaByIdUseCase,
    private val noSocialMediaRepository: NoSocialMediaRepository,
) {

    fun execute(id: Int) {
        val socialMedia = getSocialMediaByIdUseCase.execute(id)

        val newSocialMedia = socialMedia.copy(
            numberOfDays = socialMedia.numberOfDays +1
        )

        noSocialMediaRepository.insertSocialMedias(newSocialMedia)
    }
}
