package com.falon.nosocialmedia.android.di

import android.content.Context
import android.content.SharedPreferences
import com.falon.nosocialmedia.data.NoSocialMediaDatabase
import com.falon.nosocialmedia.socialcounter.data.DatabaseDriverFactory
import com.falon.nosocialmedia.socialcounter.data.KeyValuePersistentStorage
import com.falon.nosocialmedia.socialcounter.data.KeyValuePersistentStorageImpl
import com.falon.nosocialmedia.socialcounter.data.NoSocialMediasRepository
import com.falon.nosocialmedia.socialcounter.domain.IncreaseNoMediaCounterUseCase
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
            NoSocialMediaDatabase(DatabaseDriverFactory(context).provide()),
        )

    @Provides
    fun provideIncreaseNoMediaCounterUseCase(
        noSocialMediaRepository: NoSocialMediasRepository
    ): IncreaseNoMediaCounterUseCase = IncreaseNoMediaCounterUseCase(
        noSocialMediaRepository = noSocialMediaRepository,
    )
}
