package com.falon.habit.domain.usecase

import com.falon.habit.domain.contract.UserRepository
import com.falon.habit.domain.model.User

class RegisterUserUseCase(
    private val userRepository: UserRepository,
) {

    suspend fun execute(user: User) =
        userRepository.insertUser(user)
}