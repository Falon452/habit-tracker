package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.model.DomainError
import com.falon.habit.habits.domain.model.Habit
import com.falon.habit.habits.domain.repository.HabitsRepository
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

class ObserveHabitsUseCase(
    private val habitsRepository: HabitsRepository,
) {

    fun execute(): Flow<List<Result<Habit, DomainError>>> =
        habitsRepository.observeHabits()
}
