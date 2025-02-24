package com.falon.habit.habits.domain.contract

import com.falon.habit.habits.domain.model.User

interface UserRepository {

    suspend fun getUser(email: String): User?

    suspend fun insertUser(user: User)
}
