package com.falon.habit.domain

import com.falon.habit.domain.HabitCounter.Companion.getIncreasedCounter
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.asErr
import com.falon.habit.data.HabitsRepository

class IncreaseNoMediaCounterUseCase(
    private val noSocialMediaRepository: HabitsRepository,
) {

    fun execute(id: UInt) : Result<Unit, DomainError> {
        val socialMediaResult = noSocialMediaRepository.getSocialMedia(id)
        if (socialMediaResult.isErr) {
            return socialMediaResult.asErr()
        }

        val newSocialMedia = socialMediaResult.value
        val increasedCounter = newSocialMedia.getIncreasedCounter()

        if (increasedCounter.isErr) {
            return increasedCounter.asErr()
        }

        return noSocialMediaRepository.replaceSocialMedias(increasedCounter.value)
    }
}
