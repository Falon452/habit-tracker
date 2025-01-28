package com.falon.habit.data

import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.utils.CommonFlow
import com.falon.habit.utils.toCommonFlow
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMapEither
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class InMemoryHabitsRepository(
    db: HabitDatabase,
): HabitsRepository {

    private val queries = db.habitsdbQueries

    override fun observeSocialMedias(): CommonFlow<List<Result<HabitCounter, DomainError>>> {
        return queries.getSocialMedias().asFlow().mapToList().map { habits ->
            habits.map {
                HabitCounter.firstCreation(
                    it.id.toString(),
                    it.daysCount.toInt(),
                    it.name,
                    it.lastIncreaseTimestamp,
                )
            }
        }.toCommonFlow()
    }

    override suspend fun insertHabits(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError> {
        return runCatching {
            queries.insertSocialMediaEntity(
                habitCounter.name.value,
                habitCounter.numberOfDays.toLong(),
                habitCounter.lastIncreaseDateTime.toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
            )
        }.mapError { DomainError.DatabaseError(it) }
    }

    override suspend fun replaceHabits(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError> {
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


    override suspend fun getHabit(id: String): Result<HabitCounter, DomainError> {
        return runCatching { queries.getSocialMedia(id.toLong()).executeAsOne() }
            .flatMapEither(
                failure = { Err(DomainError.DatabaseError(it)) },
                success = {
                    HabitCounter.firstCreation(
                        it.id.toString(),
                        it.daysCount.toInt(),
                        it.name,
                        it.lastIncreaseTimestamp,
                    )
                }
            )
    }

}