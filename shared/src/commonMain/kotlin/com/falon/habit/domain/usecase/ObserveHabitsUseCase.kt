package com.falon.habit.domain.usecase

import com.falon.habit.domain.contract.HabitsRepository
import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.Habit
import com.falon.habit.utils.CommonFlow
import com.github.michaelbull.result.Result

class ObserveHabitsUseCase(
    private val habitsRepository: HabitsRepository,
) {

    fun execute(): CommonFlow<List<Result<Habit, DomainError>>> =
        habitsRepository.observeHabits()
}
