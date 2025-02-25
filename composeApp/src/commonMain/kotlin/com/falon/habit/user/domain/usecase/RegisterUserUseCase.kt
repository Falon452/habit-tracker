package com.falon.habit.user.domain.usecase

import com.falon.habit.habits.domain.model.User
import com.falon.habit.user.domain.repository.UserRepository

class RegisterUserUseCase(
    private val userRepository: UserRepository,
) {

    suspend fun execute(user: User) =
        userRepository.insertUser(user)
}
