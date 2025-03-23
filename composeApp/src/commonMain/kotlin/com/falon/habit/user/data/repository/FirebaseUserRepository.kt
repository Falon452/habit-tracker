package com.falon.habit.user.data.repository

import com.falon.habit.habits.domain.model.User
import com.falon.habit.user.domain.repository.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore


class FirebaseUserRepository : UserRepository {
    private val firestore = Firebase.firestore

    // Retrieve a user by email
    override suspend fun getUser(email: String): User? {
        return try {
            val query = firestore.collection("USERS").where {
                "email" equalTo email
            }

            val querySnapshot = query.get()

            querySnapshot.documents.firstOrNull()?.data<User>()
        } catch (e: Exception) {
            println("Error retrieving user by email: ${e.message}")
            null
        }
    }

    override suspend fun insertUser(user: User) {
        try {
            firestore.collection("USERS").document(user.uid).set(user)
        } catch (e: Exception) {
            println("Error inserting user: ${e.message}")
        }
    }

    override fun getCurrentUser(): User? {
        val current = Firebase.auth.currentUser ?: return null
        return with(current) {
            User(
                email = email,
                uid = uid,
            )
        }
    }
}
