package com.falon.nosocialmedia.socialcounter.domain.repository

import com.falon.nosocialmedia.core.domain.flow.CommonFlow
import com.falon.nosocialmedia.socialcounter.domain.model.DomainError
import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter
import com.github.michaelbull.result.Result

interface NoSocialMediaRepository {

    fun observeSocialMedias(): CommonFlow<List<Result<HabitCounter, DomainError>>>

    fun insertSocialMedias(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError>

    fun getSocialMedia(id: Int): Result<HabitCounter, DomainError>

    fun initializeDatabase(): List<DomainError>
}