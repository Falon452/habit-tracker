package com.falon.nosocialmedia.socialcounter.data.repository

import com.falon.nosocialmedia.core.domain.flow.CommonFlow
import com.falon.nosocialmedia.core.domain.flow.toCommonFlow
import com.falon.nosocialmedia.data.NoSocialMediaDatabase
import com.falon.nosocialmedia.socialcounter.domain.model.DomainError
import com.falon.nosocialmedia.socialcounter.domain.model.HabitCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.filterErrors
import com.github.michaelbull.result.filterValues
import com.github.michaelbull.result.flatMapEither
import com.github.michaelbull.result.mapEither
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map

class NoSocialMediasRepositoryImpl(
    private val db: NoSocialMediaDatabase,
) : NoSocialMediaRepository {

    private val queries = db.socialmediasQueries

    override fun observeSocialMedias(): CommonFlow<List<Result<HabitCounter, DomainError>>> {
        return queries.getSocialMedias().asFlow().mapToList().map { socialMediaEntities ->
            socialMediaEntities.map {
                HabitCounter.of(
                    it.id.toInt(),
                    it.daysCount.toInt(),
                    it.name,
                )
            }
        }.toCommonFlow()
    }

    override fun insertSocialMedias(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError> {
        return runCatching {
            queries.insertSocialMediaEntity(
                habitCounter.id.toLong(),
                habitCounter.name.value,
                habitCounter.numberOfDays.toLong()
            )
        }.mapError {  DomainError.DatabaseError(it) }
    }

    override fun getSocialMedia(id: Int): Result<HabitCounter, DomainError> {
        return runCatching { queries.getSocialMedia(id.toLong()).executeAsOne() }
            .flatMapEither(
                failure = { Err(DomainError.DatabaseError(it)) },
                success = {
                    HabitCounter.of(
                        it.id.toInt(),
                        it.daysCount.toInt(),
                        it.name,
                    )
                }
            )
    }

    override fun initializeDatabase(): List<DomainError> {
        val existingItems = queries.getSocialMedias().executeAsList()
        if (existingItems.isEmpty()) {
            val predefinedItems = listOf(
                HabitCounter.of(1, 1, "Instagram"),
                HabitCounter.of(2, 1, "Instagram"),
            )
            predefinedItems.filterValues().forEach(::insertSocialMedias)
            return predefinedItems.filterErrors()
        } else {
            return listOf(DomainError.DatabaseIsAlreadyPopulated)
        }
    }
}