package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.model.DomainError
import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository
import com.github.michaelbull.result.Result

class GetSocialMediaByIdUseCase(
    private val noSocialMediaRepository: NoSocialMediaRepository,
) {

    fun execute(id: Int): Result<HabitCounter, DomainError> {
        return noSocialMediaRepository.getSocialMedia(id)
    }
}