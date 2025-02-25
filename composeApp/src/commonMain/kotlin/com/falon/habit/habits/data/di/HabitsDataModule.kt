package com.falon.habit.habits.data.di

import com.falon.habit.habits.data.FirestoreHabitsRepository
import com.falon.habit.habits.data.mapper.HabitDataMapper
import com.falon.habit.habits.domain.repository.HabitsRepository
import com.falon.habit.habits.domain.specification.HabitDisabledSpec
import com.falon.habit.habits.domain.usecase.AddHabitUseCase
import com.falon.habit.habits.domain.usecase.IncreaseHabitStreakUseCase
import com.falon.habit.habits.domain.usecase.ObserveHabitsUseCase
import com.falon.habit.habits.domain.usecase.ShareHabitWithUseCase
import com.falon.habit.habits.presentation.mapper.HabitItemMapper
import com.falon.habit.habits.presentation.mapper.HabitsViewStateMapper
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val habitsDataModule = module {
    factory { HabitDisabledSpec(clock = Clock.System, timeZone = TimeZone.currentSystemDefault()) }
    factory { HabitDataMapper(get(), timeZone = TimeZone.currentSystemDefault()) }
    singleOf(::FirestoreHabitsRepository) { bind<HabitsRepository>() }
    factoryOf(::IncreaseHabitStreakUseCase)
    factoryOf(::ShareHabitWithUseCase)
    factoryOf(::ObserveHabitsUseCase)
    factoryOf(::AddHabitUseCase)
    factoryOf(::HabitsViewStateMapper)
    factoryOf(::HabitItemMapper)
}
