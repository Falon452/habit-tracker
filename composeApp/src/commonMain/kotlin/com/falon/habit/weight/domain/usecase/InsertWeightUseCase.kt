package com.falon.habit.weight.domain.usecase

import com.falon.habit.user.domain.repository.UserRepository
import com.falon.habit.weight.domain.model.BonesItem
import com.falon.habit.weight.domain.model.FatItem
import com.falon.habit.weight.domain.model.MuscleItem
import com.falon.habit.weight.domain.model.WaterItem
import com.falon.habit.weight.domain.model.WeightHistory
import com.falon.habit.weight.domain.model.WeightItem
import com.falon.habit.weight.domain.repository.WeightRepository
import com.falon.habit.weight.domain.usecase.InsertWeightUseCase.Companion.InsertWeightError.NoInternet
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class InsertWeightUseCase(
    private val weightRepository: WeightRepository,
    private val userRepository: UserRepository,
) {

    suspend fun execute(
        weight: Float?,
        fat: Float?,
        muscle: Float?,
        water: Float?,
        bones: Float?,
        weightGoal: Float?,
        fatGoal: Float?,
        muscleGoal: Float?,
        waterGoal: Float?,
        bmiGoal: Float?,
        bonesGoal: Float?,
        weightHistory: WeightHistory?,
    ): Result<Unit, InsertWeightError> {
        val currentUser = userRepository.getCurrentUser()
            ?: return Err(InsertWeightError.NoUser("No user is logged in."))
        weightHistory ?: return Err(NoInternet("No internet")) // This is not to lose data

        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val updatedWeightHistory = weightHistory.copy(
            userUid = currentUser.uid,
            weightGoal = weightGoal,
            weights = weight?.let { weightHistory.weights.plus(WeightItem(weight, currentTime)) }
                ?: weightHistory.weights,
            fatGoal = fatGoal,
            fats = fat?.let { weightHistory.fats.plus(FatItem(fat, currentTime)) }
                ?: weightHistory.fats,
            muscleGoal = muscleGoal,
            muscles = muscle?.let { weightHistory.muscles.plus(MuscleItem(muscle, currentTime)) }
                ?: weightHistory.muscles,
            waterGoal = waterGoal,
            waters = water?.let { weightHistory.waters.plus(WaterItem(water, currentTime)) }
                ?: weightHistory.waters,
            bmiGoal = bmiGoal,
            bonesGoal = bonesGoal,
            bones = bones?.let { weightHistory.bones.plus(BonesItem(bones, currentTime)) }
                ?: weightHistory.bones,
        )

        return weightRepository.insertWeightHistory(updatedWeightHistory)
    }

    companion object {

        sealed class InsertWeightError(open val msg: String) {

            data class NoInternet(override val msg: String) : InsertWeightError(msg)
            data class NoUser(override val msg: String) : InsertWeightError(msg)
            data class SomethingWentWrong(override val msg: String) : InsertWeightError(msg)
        }
    }
}
