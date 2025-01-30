package com.falon.habit.di

import android.content.Context
import android.content.SharedPreferences
import com.falon.habit.data.FirebaseUserRepository
import com.falon.habit.data.FirestoreHabitsRepository
import com.falon.habit.data.HabitsRepository
import com.falon.habit.data.KeyValuePersistentStorage
import com.falon.habit.data.KeyValuePersistentStorageImpl
import com.falon.habit.data.UserRepository
import com.falon.habit.domain.usecase.AddNewHabitUseCase
import com.falon.habit.domain.usecase.IncreaseNoMediaCounterUseCase
import com.falon.habit.domain.usecase.IsHabitDisabledUseCase
import com.falon.habit.domain.usecase.ObserveHabitsUseCase
import com.falon.habit.domain.usecase.RegisterUserUseCase
import com.falon.habit.domain.usecase.ShareHabitWithUseCase
import com.falon.habit.presentation.mapper.HabitItemMapper
import com.falon.habit.presentation.mapper.HabitsViewStateMapper
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
    fun provideAddNewHabitUseCase(
        habitsRepository: HabitsRepository,
    ): AddNewHabitUseCase = AddNewHabitUseCase(
        repository = habitsRepository,
    )

    @Provides
    fun provideObserveHabitsUseCase(
        habitsRepository: HabitsRepository
    ) = ObserveHabitsUseCase(
        repository = habitsRepository,
    )

    @Provides
    fun provideIsHabitDisabledUseCase() =
        IsHabitDisabledUseCase()

    @Provides
    fun provideHabitItemMapper(isHabitDisabledUseCase: IsHabitDisabledUseCase) = HabitItemMapper(
        isHabitDisabledUseCase
    )

    @Provides
    fun provideHabitViewStateMapper(
        habitItemMapper: HabitItemMapper
    ) = HabitsViewStateMapper(habitItemMapper)

    @Provides
    fun provideShareHabitWithUseCase(
        habitsRepository: HabitsRepository,
        userRepository: UserRepository,
    ): ShareHabitWithUseCase = ShareHabitWithUseCase(
        habitsRepository = habitsRepository,
        userRepository = userRepository,
    )
}
