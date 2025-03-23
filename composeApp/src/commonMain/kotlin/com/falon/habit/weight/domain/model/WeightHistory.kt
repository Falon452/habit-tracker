package com.falon.habit.weight.domain.model

data class WeightHistory(
    val userUid: String,
    val height: Float? = null,
    val weightGoal: Float? = null,
    val weights: List<WeightItem> = emptyList(),
    val fatGoal: Float? = null,
    val fats: List<FatItem> = emptyList(),
    val muscleGoal: Float? = null,
    val muscles: List<MuscleItem> = emptyList(),
    val waterGoal: Float? = null,
    val waters: List<WaterItem> = emptyList(),
    val bmiGoal: Float? = null,
    val bonesGoal: Float? = null,
    val bones: List<BonesItem> = emptyList(),
)
