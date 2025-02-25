package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.model.Habit
import com.falon.habit.habits.domain.repository.HabitsRepository

class AddHabitUseCase(
    private val habitsRepository: HabitsRepository,
) {

    suspend fun execute(habit: Habit) = habitsRepository.insertHabit(habit)
}
