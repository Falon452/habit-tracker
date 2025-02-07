package com.falon.habit.data

import com.falon.habit.data.mapper.HabitDataMapper
import com.falon.habit.data.model.HabitData
import com.falon.habit.domain.contract.HabitsRepository
import com.falon.habit.domain.model.DomainError
import com.falon.habit.domain.model.Habit
import com.falon.habit.utils.CommonFlow
import com.falon.habit.utils.toCommonFlow
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class FirestoreHabitsRepository(
    private val habitDataMapper: HabitDataMapper,
) : HabitsRepository {

    private val firestore = Firebase.firestore

    override fun observeHabits(): CommonFlow<List<Result<Habit, DomainError>>> =
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
                    documentSnapshot.data<HabitData>()
                }
                emit(habits.map(habitDataMapper::from).map { Ok(it) })
            }
        }.toCommonFlow()


    override suspend fun insertHabit(habit: Habit): Result<Unit, DomainError.DatabaseError> {
        return try {
            val generatedId = firestore.collection("HABITS").document.id
            val synchronizedHabit = habit.copy(id = generatedId)
            val habitData = habitDataMapper.from(synchronizedHabit)
            firestore.collection("HABITS").document(generatedId).set(habitData)
            Ok(Unit)
        } catch (e: Exception) {
            Err(DomainError.DatabaseError)
        }
    }

    override suspend fun replaceHabit(habit: Habit): Result<Unit, DomainError.DatabaseError> {
        val habitData = habitDataMapper.from(habit)
        firestore.collection("HABITS")
            .document(habit.id)
            .set(habitData)
        return Ok(Unit)
    }

    override suspend fun getHabit(id: String): Result<Habit, DomainError> {
        val habit = firestore.collection("HABITS")
            .document(id)
            .get()
            .data<HabitData>()
        return Ok(habitDataMapper.from(habit))
    }
}
