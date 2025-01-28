package com.falon.habit.domain.usecase

import com.falon.habit.data.HabitsRepository
import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.HabitCounter.Companion.getIncreasedCounter
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.asErr

class IncreaseNoMediaCounterUseCase(
    private val noSocialMediaRepository: HabitsRepository,
) {

    suspend fun execute(id: String) : Result<Unit, DomainError> {
        val socialMediaResult = noSocialMediaRepository.getHabit(id)
        if (socialMediaResult.isErr) {
            return socialMediaResult.asErr()
        }

        val newSocialMedia = socialMediaResult.value
        val increasedCounter = newSocialMedia.getIncreasedCounter()

        if (increasedCounter.isErr) {
            return increasedCounter.asErr()
        }

        return noSocialMediaRepository.replaceHabits(increasedCounter.value)
    }
}
