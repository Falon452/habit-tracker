package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.core.domain.flow.CommonFlow
import com.falon.nosocialmedia.socialcounter.domain.model.DomainError
import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository
import com.github.michaelbull.result.Result

class ObserveSocialMediaUseCase(
    private val repository: NoSocialMediaRepository
) {

    fun execute(): CommonFlow<List<Result<HabitCounter, DomainError>>> {
        return repository.observeSocialMedias()
    }
}