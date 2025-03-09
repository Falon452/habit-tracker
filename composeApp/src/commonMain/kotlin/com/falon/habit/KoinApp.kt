package com.falon.habit

import com.falon.habit.habits.data.di.habitsDataModule
import com.falon.habit.habits.presentation.di.habitsPresentationModule
import com.falon.habit.login.presentation.di.loginModule
import com.falon.habit.register.presentation.di.registerModule
import com.falon.habit.splash.presentation.di.splashPresentationModule
import com.falon.habit.user.data.di.userDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        printLogger()
        includes(config)
        modules(
            habitsDataModule,
            userDataModule,
            habitsPresentationModule,
            splashPresentationModule,
            loginModule,
            registerModule,
        )
    }
}
