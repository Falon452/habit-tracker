package com.falon.habit.habits.data

import com.falon.habit.data.HabitDatabase
import com.falon.habit.habits.domain.contract.HabitsRepository
import com.falon.habit.habits.domain.model.DomainError
import com.falon.habit.habits.domain.model.Habit
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMapEither
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class InMemoryHabitsRepository(
    db: HabitDatabase,
) : HabitsRepository {

    private val queries = db.habitsdbQueries

    override fun observeHabits(): Flow<List<Result<Habit, DomainError>>> {
        return queries.getSocialMedias().asFlow().mapToList().map { habits ->
            habits.map {
                Habit.create(
                    it.id.toString(),
                    it.daysCount.toInt(),
                    it.name,
                    it.lastIncreaseTimestamp,
                )
            }
        }
    }

    override suspend fun insertHabit(habit: Habit): Result<Unit, DomainError.DatabaseError> {
        return runCatching {
            queries.insertSocialMediaEntity(
                habit.name.value,
                habit.numberOfDays.toLong(),
                habit.streakDateTimes.last().toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
            )
        }.mapError { DomainError.DatabaseError }
    }

    override suspend fun replaceHabit(habit: Habit): Result<Unit, DomainError.DatabaseError> {
        return runCatching {
            queries.replaceSocialMediaEntity(
                habit.id.toLong(),
                habit.name.value,
                habit.numberOfDays.toLong(),
                habit.streakDateTimes.last().toInstant(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
            )
        }.mapError { DomainError.DatabaseError }
    }


    override suspend fun getHabit(id: String): Result<Habit, DomainError> {
        return runCatching { queries.getSocialMedia(id.toLong()).executeAsOne() }
            .flatMapEither(
                failure = { Err(DomainError.DatabaseError) },
                success = {
                    Habit.create(
                        it.id.toString(),
                        it.daysCount.toInt(),
                        it.name,
                        it.lastIncreaseTimestamp,
                    )
                }
            )
    }

}
