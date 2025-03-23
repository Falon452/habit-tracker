package com.falon.habit.weight.presentation.effects

sealed interface WeightHistoryEvents {

    data class ShowToast(val message: String) : WeightHistoryEvents
}
