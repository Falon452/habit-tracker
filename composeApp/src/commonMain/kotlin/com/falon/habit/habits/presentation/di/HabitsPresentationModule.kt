package com.falon.habit.habits.presentation.di

import com.falon.habit.habits.presentation.viewmodel.HabitsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val habitsPresentationModule = module {
    viewModelOf(::HabitsViewModel)
}
