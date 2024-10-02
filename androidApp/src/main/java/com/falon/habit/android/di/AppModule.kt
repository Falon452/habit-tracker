package com.falon.habit.android.di

import android.content.Context
import android.content.SharedPreferences
import com.falon.habit.data.HabitDatabase
import com.falon.habit.data.DatabaseDriverFactory
import com.falon.habit.data.KeyValuePersistentStorage
import com.falon.habit.data.KeyValuePersistentStorageImpl
import com.falon.habit.data.NoSocialMediasRepository
import com.falon.habit.domain.IncreaseNoMediaCounterUseCase
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
    fun provideNoSocialMediasCounterRepository(
        @ApplicationContext context: Context,
    ): NoSocialMediasRepository =
        NoSocialMediasRepository(
            HabitDatabase(DatabaseDriverFactory(context).provide()),
        )

    @Provides
    fun provideIncreaseNoMediaCounterUseCase(
        noSocialMediaRepository: NoSocialMediasRepository
    ): IncreaseNoMediaCounterUseCase = IncreaseNoMediaCounterUseCase(
        noSocialMediaRepository = noSocialMediaRepository,
    )
}
