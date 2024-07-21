package com.falon.nosocialmedia.socialcounter.domain.di

import com.falon.nosocialmedia.socialcounter.domain.interactor.IncreaseNoMediaCounterUseCase
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediasCounterRepository

abstract class SocialCounterDomainModule{

    abstract val repository: NoSocialMediasCounterRepository

    fun provideIncreaseNoMediaCounterUseCase(): IncreaseNoMediaCounterUseCase =
        IncreaseNoMediaCounterUseCase(noSocialMediasCounterRepository = repository)
}
