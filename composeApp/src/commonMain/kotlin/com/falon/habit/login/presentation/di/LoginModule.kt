package com.falon.habit.login.presentation.di

import com.falon.habit.login.presentation.mapper.LoginViewStateMapper
import com.falon.habit.login.presentation.viewmodel.LoginViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    single { Firebase.auth }
    factoryOf(::LoginViewStateMapper)
    viewModelOf(::LoginViewModel)
}
expect val loginPlatformModule: Module
