package com.falon.habit.data

import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.HabitCounter
import com.falon.habit.utils.CommonFlow
import com.falon.habit.utils.toCommonFlow
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow


class FirestoreHabitsRepository : HabitsRepository {

    private val firestore = Firebase.firestore

    override fun observeHabits(): CommonFlow<List<Result<HabitCounter, DomainError>>> =
        flow {
            val currentUserUid = Firebase.auth.currentUser?.uid
                ?: throw Exception("User not authenticated")

            val query = firestore.collection("HABITS")
                .where {
                    any(
                        "userUid" equalTo currentUserUid,
                        "sharedWithUids" contains currentUserUid
                    )
                }

            query.snapshots.collect { querySnapshot ->
                val habits = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<HabitCounter>()
                }
                println("Filtered habits: $habits")
                emit(habits.map { Ok(it) })
            }
        }.toCommonFlow()



    override suspend fun insertHabit(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError> {
        return try {
            val generatedId = firestore.collection("HABITS").document.id

            val synchronizedHabitCounter = habitCounter.copy(
                id = generatedId
            )

            firestore.collection("HABITS")
                .document(generatedId)
                .set(synchronizedHabitCounter)

            println("HabitCounter added with ID: $generatedId")
            Ok(Unit)
        } catch (e: Exception) {
            println("Error inserting habitCounter: ${e.message}")
            Err(DomainError.DatabaseError(e))
        }
    }

    override suspend fun replaceHabits(habitCounter: HabitCounter): Result<Unit, DomainError.DatabaseError> {
        firestore.collection("HABITS")
            .document(habitCounter.id)
            .set(habitCounter)
        return Ok(Unit)
    }

    override suspend fun getHabit(id: String): Result<HabitCounter, DomainError> {
        val habitCounter = firestore.collection("HABITS")
            .document(id)
            .get()
            .data<HabitCounter>()
        return Ok(habitCounter)
    }
}