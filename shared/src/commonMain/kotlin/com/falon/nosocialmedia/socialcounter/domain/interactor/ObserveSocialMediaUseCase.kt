package com.falon.nosocialmedia.socialcounter.domain.interactor

import com.falon.nosocialmedia.core.domain.flow.CommonFlow
import com.falon.nosocialmedia.socialcounter.domain.model.NoSocialCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository

class ObserveSocialMediaUseCase(
    private val repository: NoSocialMediaRepository
) {

    fun execute(): CommonFlow<List<NoSocialCounter>> {
        return repository.observeSocialMedias()
    }
}