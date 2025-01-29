package com.falon.habit.data

import com.falon.habit.domain.model.User

interface UserRepository {

    suspend fun getUser(email: String): User?

    suspend fun insertUser(user: User)
}