package com.falon.habit.weight.domain.repository

import com.falon.habit.weight.domain.model.WeightHistory
import com.falon.habit.weight.domain.usecase.InsertWeightUseCase.Companion.InsertWeightError
import com.falon.habit.weight.domain.usecase.ObserveWeightUseCase.Companion.ObserveWeightError
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow

interface WeightRepository {

    fun observeWeightHistory(uid: String): Flow<Result<WeightHistory, ObserveWeightError>>

    suspend fun insertWeightHistory(weight: WeightHistory): Result<Unit, InsertWeightError>
}
