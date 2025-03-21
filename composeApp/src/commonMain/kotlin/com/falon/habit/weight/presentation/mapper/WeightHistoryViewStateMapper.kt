package com.falon.habit.weight.presentation.mapper

import com.falon.habit.weight.presentation.model.WeightHistoryState
import com.falon.habit.weight.presentation.model.WeightHistoryViewState

class WeightHistoryViewStateMapper {

    fun from(state: WeightHistoryState): WeightHistoryViewState {
        return WeightHistoryViewState(
            weightHistory = state.weightHistory,
            weight = state.weight?.toString() ?: "",
            weightError = isWeightInvalid(state.weight),
            weightErrorMessage = if (isWeightInvalid(state.weight)) "Weight must be between 20 and 300" else "",
            fat = state.fat?.toString() ?: "",
            fatError = isFatInvalid(state.fat),
            fatErrorMessage = if (isFatInvalid(state.fat)) "Fat must be between 0 and 100" else "",
            muscle = state.muscle?.toString() ?: "",
            muscleError = isMuscleInvalid(state.muscle),
            muscleErrorMessage = if (isMuscleInvalid(state.muscle)) "Muscle must be between 0 and 100" else "",
            water = state.water?.toString() ?: "",
            waterError = isWaterInvalid(state.water),
            waterErrorMessage = if (isWaterInvalid(state.water)) "Water must be between 0 and 100" else "",
            bmi = state.bmi?.toString() ?: "",
            bmiError = isBmiInvalid(state.bmi),
            bmiErrorMessage = if (isBmiInvalid(state.bmi)) "BMI must be between 10 and 50" else "",
            bones = state.bones?.toString() ?: "",
            bonesError = isBonesInvalid(state.bones),
            bonesErrorMessage = if (isBonesInvalid(state.bones)) "Bones must be between 0 and 10" else "",
            weightGoal = state.weightGoal?.toString() ?: "",
            weightGoalError = isWeightGoalInvalid(state.weightGoal),
            weightGoalErrorMessage = if (isWeightGoalInvalid(state.weightGoal)) "Weight goal must be between 20 and 300" else "",
            fatGoal = state.fatGoal?.toString() ?: "",
            fatGoalError = isFatGoalInvalid(state.fatGoal),
            fatGoalErrorMessage = if (isFatGoalInvalid(state.fatGoal)) "Fat goal must be between 0 and 100" else "",
            muscleGoal = state.muscleGoal?.toString() ?: "",
            muscleGoalError = isMuscleGoalInvalid(state.muscleGoal),
            muscleGoalErrorMessage = if (isMuscleGoalInvalid(state.muscleGoal)) "Muscle goal must be between 0 and 100" else "",
            waterGoal = state.waterGoal?.toString() ?: "",
            waterGoalError = isWaterGoalInvalid(state.waterGoal),
            waterGoalErrorMessage = if (isWaterGoalInvalid(state.waterGoal)) "Water goal must be between 0 and 100" else "",
            bmiGoal = state.bmiGoal?.toString() ?: "",
            bmiGoalError = isBmiGoalInvalid(state.bmiGoal),
            bmiGoalErrorMessage = if (isBmiGoalInvalid(state.bmiGoal)) "BMI goal must be between 10 and 50" else "",
            bonesGoal = state.bonesGoal?.toString() ?: "",
            bonesGoalError = isBonesGoalInvalid(state.bonesGoal),
            bonesGoalErrorMessage = if (isBonesGoalInvalid(state.bonesGoal)) "Bones goal must be between 0 and 10" else "",
        )
    }

    private fun isWeightInvalid(weight: Float?): Boolean {
        return weight != null && (weight < 20 || weight > 300)
    }

    private fun isFatInvalid(fat: Float?): Boolean {
        return fat != null && (fat < 0 || fat > 100)
    }

    private fun isMuscleInvalid(muscle: Float?): Boolean {
        return muscle != null && (muscle < 0 || muscle > 100)
    }

    private fun isWaterInvalid(water: Float?): Boolean {
        return water != null && (water < 0 || water > 100)
    }

    private fun isBmiInvalid(bmi: Float?): Boolean {
        return bmi != null && (bmi < 10 || bmi > 50)
    }

    private fun isBonesInvalid(bones: Float?): Boolean {
        return bones != null && (bones < 0 || bones > 10)
    }

    private fun isWeightGoalInvalid(weightGoal: Float?): Boolean {
        return weightGoal != null && (weightGoal < 20 || weightGoal > 300)
    }

    private fun isFatGoalInvalid(fatGoal: Float?): Boolean {
        return fatGoal != null && (fatGoal < 0 || fatGoal > 100)
    }

    private fun isMuscleGoalInvalid(muscleGoal: Float?): Boolean {
        return muscleGoal != null && (muscleGoal < 0 || muscleGoal > 100)
    }

    private fun isWaterGoalInvalid(waterGoal: Float?): Boolean {
        return waterGoal != null && (waterGoal < 0 || waterGoal > 100)
    }

    private fun isBmiGoalInvalid(bmiGoal: Float?): Boolean {
        return bmiGoal != null && (bmiGoal < 10 || bmiGoal > 50)
    }

    private fun isBonesGoalInvalid(bonesGoal: Float?): Boolean {
        return bonesGoal != null && (bonesGoal < 0 || bonesGoal > 10)
    }
}
