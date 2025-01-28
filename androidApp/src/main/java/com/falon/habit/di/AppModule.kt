package com.falon.habit.di

import android.content.Context
import android.content.SharedPreferences
import com.falon.habit.data.FirebaseUserRepository
import com.falon.habit.data.FirestoreHabitsRepository
import com.falon.habit.data.HabitsRepository
import com.falon.habit.data.KeyValuePersistentStorage
import com.falon.habit.data.KeyValuePersistentStorageImpl
import com.falon.habit.data.UserRepository
import com.falon.habit.domain.usecase.IncreaseNoMediaCounterUseCase
import com.falon.habit.domain.usecase.RegisterUserUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    @Provides
    fun provideKeyValuePersistentStorage(sharedPreferences: SharedPreferences): KeyValuePersistentStorage =
        KeyValuePersistentStorageImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideHabitsCounterRepository(): HabitsRepository =
        FirestoreHabitsRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository =
        FirebaseUserRepository()

    @Provides
    fun provideIncreaseNoMediaCounterUseCase(
        noSocialMediaRepository: HabitsRepository
    ): IncreaseNoMediaCounterUseCase = IncreaseNoMediaCounterUseCase(
        noSocialMediaRepository = noSocialMediaRepository,
    )

    @Provides
    fun provideRegisterUserUseCase(
        userRepository: UserRepository,
    ): RegisterUserUseCase = RegisterUserUseCase(
        userRepository = userRepository,
    )

    @Provides
    fun provideShareHabitWithUseCase(
        habitsRepository: HabitsRepository,
        userRepository: UserRepository,
    ): ShareHabitWithUseCase = ShareHabitWithUseCase(
        habitsRepository = habitsRepository,
        userRepository = userRepository,
    )
}
