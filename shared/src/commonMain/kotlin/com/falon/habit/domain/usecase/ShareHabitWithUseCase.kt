package com.falon.habit.domain.usecase

import com.falon.habit.data.HabitsRepository
import com.falon.habit.data.UserRepository
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok

class ShareHabitWithUseCase(
    private val userRepository: UserRepository,
    private val habitsRepository: HabitsRepository
) {


    suspend fun execute(
        email: String,
        habitId: String,
    ): Result<Unit, ShareHabitWithUseCaseResult> {
        userRepository.getUser(email)
            ?.let { user ->
                val habit = habitsRepository.getHabit(habitId)
                if (habit.isOk) {
                    val habitCounter = habit.value

                    val sharedHabit = habitCounter.copy(
                        sharedWithUids = habitCounter.sharedWithUids.plus(user.uid)
                    )
                    habitsRepository.replaceHabits(sharedHabit)
                }
            }
            ?: return Err(ShareHabitWithUseCaseResult.NoUserWithEmailFound(email))
        return Ok(Unit)
    }
}

sealed interface ShareHabitWithUseCaseResult {

    data class NoUserWithEmailFound(val email: String) : ShareHabitWithUseCaseResult
}