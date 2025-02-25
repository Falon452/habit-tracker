package com.falon.habit.habits.data

import com.falon.habit.habits.domain.contract.UserRepository
import com.falon.habit.habits.domain.model.User
import dev.gitlive.firebase.Firebase
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
}
