package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository

class PopulateDatabaseUseCase(
    private val repository: NoSocialMediaRepository,
) {

    fun execute() {
        return repository.initializeDatabase()
    }
}