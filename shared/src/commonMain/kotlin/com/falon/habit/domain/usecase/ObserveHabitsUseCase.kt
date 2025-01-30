package com.falon.habit.domain.usecase

import com.falon.habit.data.HabitsRepository
import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.utils.CommonFlow
import com.github.michaelbull.result.Result

class ObserveHabitsUseCase(
    private val repository: HabitsRepository,
) {

    fun execute(): CommonFlow<List<Result<HabitCounter, DomainError>>> =
        repository.observeHabits()
}