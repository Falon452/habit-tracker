package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.model.DomainError
import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter
import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter.Companion.getIncreasedCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.asErr

class IncreaseNoMediaCounterUseCase(
    private val getSocialMediaByIdUseCase: GetSocialMediaByIdUseCase,
    private val noSocialMediaRepository: NoSocialMediaRepository,
) {

    fun execute(id: Int) : Result<Unit, DomainError> {
        val socialMediaResult = getSocialMediaByIdUseCase.execute(id)
        if (socialMediaResult.isErr) {
            return socialMediaResult.asErr()
        }

        val newSocialMedia: HabitCounter = socialMediaResult.value
        val increasedCounter = newSocialMedia.getIncreasedCounter()

        if (increasedCounter.isErr) {
            return increasedCounter.asErr()
        }

        return noSocialMediaRepository.insertSocialMedias(increasedCounter.value)
    }
}
