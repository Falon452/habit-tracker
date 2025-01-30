package com.falon.habit.data

import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.utils.CommonFlow
import com.github.michaelbull.result.Result

interface HabitsRepository {

    fun observeHabits(): CommonFlow<List<Result<HabitCounter, DomainError>>>

    suspend fun insertHabit(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError>

    suspend fun replaceHabits(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError>

    suspend fun getHabit(id: String): Result<HabitCounter, DomainError>
}