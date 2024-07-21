package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediasCounterRepository

class IncreaseNoMediaCounterUseCase(
    private val noSocialMediasCounterRepository: NoSocialMediasCounterRepository,
) {

    fun execute(id: Int) {
        val value = noSocialMediasCounterRepository.getValue(id.toString(), FIRST_DAY)
        println("DAMIAN $value")
        noSocialMediasCounterRepository.update(id.toString(), value + 1)
    }

    private companion object {

        const val FIRST_DAY = 1
    }
}
