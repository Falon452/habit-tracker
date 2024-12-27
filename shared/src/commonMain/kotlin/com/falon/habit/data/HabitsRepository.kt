package com.falon.habit.data

import com.falon.habit.domain.DomainError
import com.falon.habit.domain.HabitCounter
import com.falon.habit.utils.CommonFlow
import com.falon.habit.utils.toCommonFlow
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.filterErrors
import com.github.michaelbull.result.filterValues
import com.github.michaelbull.result.flatMapEither
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class HabitsRepository(
    db: HabitDatabase,
) {

    private val queries = db.habitsdbQueries

    fun observeSocialMedias(): CommonFlow<List<Result<HabitCounter.HabitCounterDataClass, DomainError>>> {
        return queries.getSocialMedias().asFlow().mapToList().map { socialMediaEntities ->
            socialMediaEntities.map {
                HabitCounter.of(
                    it.id.toInt(),
                    it.daysCount.toInt(),
                    it.name,
                    it.lastIncreaseTimestamp,
                )
            }
        }.toCommonFlow()
    }

    fun insertSocialMedias(habitCounter: HabitCounter.HabitCounterDataClassFirstCreation): Result<Unit, DomainError.DatabaseError> {
        return runCatching {
            queries.insertSocialMediaEntity(
                habitCounter.name.value,
                habitCounter.numberOfDays.toLong(),
                habitCounter.lastIncreaseDateTime.toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
            )
        }.mapError { DomainError.DatabaseError(it) }
    }

    fun replaceSocialMedias(habitCounter: HabitCounter.HabitCounterDataClass): Result<Unit, DomainError.DatabaseError> {
        return runCatching {
            queries.replaceSocialMediaEntity(
                habitCounter.id.toLong(),
                habitCounter.name.value,
                habitCounter.numberOfDays.toLong(),
                habitCounter.lastIncreaseDateTime.toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
            )
        }.mapError { DomainError.DatabaseError(it) }
    }


    fun getSocialMedia(id: UInt): Result<HabitCounter.HabitCounterDataClass, DomainError> {
        return runCatching { queries.getSocialMedia(id.toLong()).executeAsOne() }
            .flatMapEither(
                failure = { Err(DomainError.DatabaseError(it)) },
                success = {
                    HabitCounter.of(
                        it.id.toInt(),
                        it.daysCount.toInt(),
                        it.name,
                        it.lastIncreaseTimestamp,
                    )
                }
            )
    }

    fun initializeDatabase(): List<DomainError> {
        val existingItems = queries.getSocialMedias().executeAsList()
        if (existingItems.isEmpty()) {
            val predefinedItems = listOf(
                HabitCounter.of(
                    "Instagram",
                ),
            )
            predefinedItems.filterValues().forEach(::insertSocialMedias)
            return predefinedItems.filterErrors()
        } else {
            return listOf(DomainError.DatabaseIsAlreadyPopulated)
        }
    }
}