package com.falon.habit.weight.data.repository

import com.falon.habit.weight.data.mapper.WeightDataMapper
import com.falon.habit.weight.data.mapper.WeightMapper
import com.falon.habit.weight.data.model.WeightHistoryData
import com.falon.habit.weight.domain.model.WeightHistory
import com.falon.habit.weight.domain.repository.WeightRepository
import com.falon.habit.weight.domain.usecase.InsertWeightUseCase
import com.falon.habit.weight.domain.usecase.InsertWeightUseCase.Companion.InsertWeightError.SomethingWentWrong
import com.falon.habit.weight.domain.usecase.ObserveWeightUseCase.Companion.ObserveWeightError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.runCatching
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeightRepositoryImpl(
    private val weightDataMapper: WeightDataMapper,
    private val weightMapper: WeightMapper,
) : WeightRepository {

    private val firestore = Firebase.firestore

    override fun observeWeightHistory(uid: String): Flow<Result<WeightHistory, ObserveWeightError>> =
        flow {
            runCatching {
                val query = firestore.collection("WEIGHT_HISTORY").where {
                    "userUid" equalTo uid
                }

                query.snapshots.collect { querySnapshot ->
                    val weightHistoryData = querySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<WeightHistoryData>()
                    }.firstOrNull()

                    if (weightHistoryData != null) {
                        emit(Ok(weightMapper.from(weightHistoryData)))
                    } else {
                        emit(Err(ObserveWeightError.SuccessButNoEntry("No Weight History")))
                    }
                }
            }.onFailure { exception ->
                emit(Err(ObserveWeightError.Unknown(exception.message ?: "Unknown error")))
            }
        }

    override suspend fun insertWeightHistory(weight: WeightHistory): Result<Unit, InsertWeightUseCase.Companion.InsertWeightError> {
        return runCatching {
            val weightHistoryData = weightDataMapper.from(weight)
            firestore.collection("WEIGHT_HISTORY")
                .document(weight.userUid)
                .set(weightHistoryData)
        }.fold(
            success = { Ok(Unit) },
            failure = { exception -> Err(SomethingWentWrong(exception.message ?: "Unknown")) }
        )
    }
}
