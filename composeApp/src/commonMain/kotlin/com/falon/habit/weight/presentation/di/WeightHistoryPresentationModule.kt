package com.falon.habit.weight.presentation.di

import com.falon.habit.weight.presentation.mapper.WeightHistoryViewStateMapper
import com.falon.habit.weight.presentation.viewmodel.WeightHistoryViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weightHistoryPresentationModule = module {
    factoryOf(::WeightHistoryViewStateMapper)
    viewModelOf(::WeightHistoryViewModel)
}
