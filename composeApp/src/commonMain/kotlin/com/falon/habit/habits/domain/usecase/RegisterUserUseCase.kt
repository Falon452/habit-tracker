package com.falon.habit.habits.domain.usecase

import com.falon.habit.habits.domain.contract.UserRepository
import com.falon.habit.habits.domain.model.User

class RegisterUserUseCase(
    private val userRepository: UserRepository,
) {

    suspend fun execute(user: User) =
        userRepository.insertUser(user)
}
