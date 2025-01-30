package com.falon.habit.domain.usecase

import com.falon.habit.data.HabitsRepository
import com.falon.habit.domain.model.HabitCounter

class AddNewHabitUseCase(
    private val repository: HabitsRepository,
){

    suspend fun execute(habitCounter: HabitCounter) = repository.insertHabit(habitCounter)
}