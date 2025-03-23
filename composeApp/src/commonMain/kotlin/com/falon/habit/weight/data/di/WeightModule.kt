package com.falon.habit.weight.data.di

import com.falon.habit.weight.data.mapper.WeightDataMapper
import com.falon.habit.weight.data.mapper.WeightMapper
import com.falon.habit.weight.data.repository.WeightRepositoryImpl
import com.falon.habit.weight.domain.repository.WeightRepository
import com.falon.habit.weight.domain.usecase.InsertWeightUseCase
import com.falon.habit.weight.domain.usecase.ObserveWeightUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val weightModule = module {
    singleOf(::WeightRepositoryImpl) { bind<WeightRepository>() }
    factoryOf(::InsertWeightUseCase)
    factoryOf(::WeightDataMapper)
    factoryOf(::WeightMapper)
    factoryOf(::ObserveWeightUseCase)
}
