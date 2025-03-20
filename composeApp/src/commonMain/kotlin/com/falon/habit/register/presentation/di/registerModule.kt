package com.falon.habit.register.presentation.di

import com.falon.habit.register.presentation.mapper.RegisterViewStateMapper
import com.falon.habit.register.presentation.viewmodel.RegisterViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val registerModule = module {
    single { Firebase.auth }
    factoryOf(::RegisterViewStateMapper)
    viewModelOf(::RegisterViewModel)
}
