package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.repository.HabitsRepository
import com.falon.habit.user.domain.repository.UserRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

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
                    habit.value
                    TODO()
//                    val sharedHabit = Habit(
//                        sharedWithUids = Habit.sharedWithUids.plus(User.uid)
//                    )
//                    habitsRepository.replaceHabit(sharedHabit)
                }
            }
            ?: return Err(ShareHabitWithUseCaseResult.NoUserWithEmailFound(email))
        return Ok(Unit)
    }
}

sealed interface ShareHabitWithUseCaseResult {

    data class NoUserWithEmailFound(val email: String) : ShareHabitWithUseCaseResult
}
