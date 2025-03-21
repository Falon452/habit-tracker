package com.falon.habit.weight.presentation.model

import com.falon.habit.weight.domain.model.WeightHistory

data class WeightHistoryState(
    val weightHistory: WeightHistory? = null,
    val weight: Float? = null,
    val fat: Float? = null,
    val muscle: Float? = null,
    val water: Float? = null,
    val bmi: Float? = null,
    val bones: Float? = null,
    val weightGoal: Float? = null,
    val fatGoal: Float? = null,
    val muscleGoal: Float? = null,
    val waterGoal: Float? = null,
    val bmiGoal: Float? = null,
    val bonesGoal: Float? = null,
)
