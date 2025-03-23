package com.falon.habit.weight.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightHistoryData(
    val userUid: String,
    val height: Float?,
    val weightGoal: Float?,
    val weights: List<WeightItemData>,
    val fatGoal: Float?,
    val fats: List<FatItemData>,
    val muscleGoal: Float?,
    val muscles: List<MuscleItemData>,
    val waterGoal: Float?,
    val waters: List<WaterItemData>,
    val bmiGoal: Float?,
    val bonesGoal: Float?,
    val bones: List<BonesItemData>,
)
