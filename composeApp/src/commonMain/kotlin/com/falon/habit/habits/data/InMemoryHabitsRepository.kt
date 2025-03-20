package com.falon.habit.habits.data

import com.falon.habit.data.HabitDatabase

class InMemoryHabitsRepository(
    db: HabitDatabase,
) {

    private val queries = db.habitsdbQueries

//    override fun observeHabits(): Flow<List<Result<Habit, DomainError>>> {
//        return queries.getSocialMedias().asFlow().mapToList().map { habits ->
//            habits.map {
//                Habit.create(
//                    it.id.toString(),
//                    it.daysCount.toInt(),
//                    it.name,
//                    it.lastIncreaseTimestamp,
//                )
//            }
//        }
//    }
//
//    override suspend fun insertHabit(habit: Habit): Result<Unit, DomainError.DatabaseError> {
//        return runCatching {
//            queries.insertSocialMediaEntity(
//                habit.name.value,
//                habit.numberOfDays.toLong(),
//                habit.streakDateTimes.last().toInstant(TimeZone.currentSystemDefault())
//                    .toEpochMilliseconds(),
//            )
//        }.mapError { DomainError.DatabaseError }
//    }
//
//    override suspend fun replaceHabit(habit: Habit): Result<Unit, DomainError.DatabaseError> {
//        return runCatching {
//            queries.replaceSocialMediaEntity(
//                habit.id.toLong(),
//                habit.name.value,
//                habit.numberOfDays.toLong(),
//                habit.streakDateTimes.last().toInstant(TimeZone.currentSystemDefault())
//                    .toEpochMilliseconds(),
//            )
//        }.mapError { DomainError.DatabaseError }
//    }
//
//
//    override suspend fun getHabit(id: String): Result<Habit, DomainError> {
//        return runCatching { queries.getSocialMedia(id.toLong()).executeAsOne() }
//            .flatMapEither(
//                failure = { Err(DomainError.DatabaseError) },
//                success = {
//                    Habit.create(
//                        it.id.toString(),
//                        it.daysCount.toInt(),
//                        it.name,
//                        it.lastIncreaseTimestamp,
//                    )
//                }
//            )
//    }

}
