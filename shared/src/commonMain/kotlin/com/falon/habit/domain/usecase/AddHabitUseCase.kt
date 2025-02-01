package com.falon.habit.domain.usecase

import com.falon.habit.domain.contract.HabitsRepository
import com.falon.habit.domain.model.Habit

class AddHabitUseCase(
    private val habitsRepository: HabitsRepository,
) {

    suspend fun execute(habit: Habit) = habitsRepository.insertHabit(habit)
}