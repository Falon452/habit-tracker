package com.falon.habit.splash.presentation.di

import com.falon.habit.splash.presentation.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashPresentationModule = module {
    viewModelOf(::SplashViewModel)
}
