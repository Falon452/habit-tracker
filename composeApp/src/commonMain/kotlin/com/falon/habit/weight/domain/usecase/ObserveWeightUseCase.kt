package com.falon.habit.weight.domain.usecase

import com.falon.habit.user.domain.repository.UserRepository
import com.falon.habit.weight.domain.model.WeightHistory
import com.falon.habit.weight.domain.repository.WeightRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ObserveWeightUseCase(
    private val weightRepository: WeightRepository,
    private val userRepository: UserRepository,
) {

    fun execute(): Flow<Result<WeightHistory, ObserveWeightError>> {
        val user = userRepository.getCurrentUser()
            ?: return flowOf(Err(ObserveWeightError.NoUser("No User logged in.")))
        return weightRepository.observeWeightHistory(user.uid)
    }

    companion object {
        sealed class ObserveWeightError(open val msg: String) {

            data class NoUser(override val msg: String) : ObserveWeightError(msg)
            data class SuccessButNoEntry(override val msg: String) : ObserveWeightError(msg)
            data class Unknown(override val msg: String) : ObserveWeightError(msg)
        }
    }
}
