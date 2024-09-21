package com.falon.nosocialmedia.socialcounter.domain

import com.falon.nosocialmedia.socialcounter.domain.HabitCounter.Companion.getIncreasedCounter
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.asErr
import com.falon.nosocialmedia.socialcounter.data.NoSocialMediasRepository

class IncreaseNoMediaCounterUseCase(
    private val noSocialMediaRepository: NoSocialMediasRepository,
) {

    fun execute(id: UInt) : Result<Unit, DomainError> {
        val socialMediaResult = noSocialMediaRepository.getSocialMedia(id)
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
