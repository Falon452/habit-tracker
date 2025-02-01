package com.falon.habit.domain.contract

import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.Habit
import com.falon.habit.utils.CommonFlow
import com.github.michaelbull.result.Result

interface HabitsRepository {

    fun observeHabits(): CommonFlow<List<Result<Habit, DomainError>>>

    suspend fun insertHabit(habit: Habit): Result<Unit, DomainError.DatabaseError>

    suspend fun replaceHabit(habit: Habit): Result<Unit, DomainError.DatabaseError>

    suspend fun getHabit(id: String): Result<Habit, DomainError>
}
