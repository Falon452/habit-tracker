package com.falon.nosocialmedia.socialcounter.presentation.model

sealed interface HabitsEffect {

    data object RequestFocusOnNewHabit: HabitsEffect

    data object HideKeyboard: HabitsEffect
}
