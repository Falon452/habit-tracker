package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.model.DomainError
import com.falon.habit.habits.domain.model.Habit
import com.falon.habit.habits.domain.repository.HabitsRepository
import com.github.michaelbull.result.Result
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class CreateHabitUseCase(
    private val habitsRepository: HabitsRepository,
) {

    suspend fun execute(name: String): Result<Unit, DomainError.DatabaseError> {
        val habit = Habit(
            numberOfDays = 0,
            name = name,
            streakDateTimes = emptyList(),
            id = generateRandomId(),
            userUid = requireNotNull(Firebase.auth.currentUser?.uid),
            sharedWithUids = emptyList(),
            isDisabled = false,
        )
        return habitsRepository.insertHabit(habit)
    }

    private fun generateRandomId(length: Int = 12): String {
        val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}
