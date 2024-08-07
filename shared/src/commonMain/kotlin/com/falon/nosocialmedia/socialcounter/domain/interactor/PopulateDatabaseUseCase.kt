package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.model.DomainError
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository

class PopulateDatabaseUseCase(
    private val repository: NoSocialMediaRepository,
) {

    fun execute(): List<DomainError> {
        return repository.initializeDatabase()
    }
}