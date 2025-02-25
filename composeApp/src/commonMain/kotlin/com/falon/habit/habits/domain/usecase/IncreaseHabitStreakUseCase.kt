package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.contract.HabitsRepository
import com.falon.habit.habits.domain.model.DomainError
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.asErr

class IncreaseHabitStreakUseCase(
    private val habitsRepository: HabitsRepository,
) {

    suspend fun execute(id: String): Result<Unit, DomainError> {
        val habit = habitsRepository.getHabit(id)
        if (habit.isErr) {
            return habit.asErr()
        }

//        val newStreak = Habit(
//            streakDateTimes = Habit.streakDateTimes.plus(
//                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
//            )
//        )

        TODO()
//        return habitsRepository.replaceHabit(habit)
    }
}
