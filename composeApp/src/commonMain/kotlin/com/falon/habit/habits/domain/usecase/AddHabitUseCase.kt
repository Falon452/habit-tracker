package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.contract.HabitsRepository
import com.falon.habit.habits.domain.model.Habit

class AddHabitUseCase(
    private val habitsRepository: HabitsRepository,
) {

    suspend fun execute(habit: Habit) = habitsRepository.insertHabit(habit)
}
