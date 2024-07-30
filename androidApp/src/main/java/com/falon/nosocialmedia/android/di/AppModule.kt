package com.falon.nosocialmedia.android.di

import android.content.Context
import android.content.SharedPreferences
import com.falon.nosocialmedia.data.NoSocialMediaDatabase
import com.falon.nosocialmedia.socialcounter.data.local.DatabaseDriverFactory
import com.falon.nosocialmedia.socialcounter.data.repository.KeyValuePersistentStorage
import com.falon.nosocialmedia.socialcounter.data.repository.NoSocialMediasRepositoryImpl
import com.falon.nosocialmedia.socialcounter.data.sources.KeyValuePersistentStorageImpl
import com.falon.nosocialmedia.socialcounter.domain.interactor.GetSocialMediaByIdUseCase
import com.falon.nosocialmedia.socialcounter.domain.interactor.IncreaseNoMediaCounterUseCase
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository
import com.falon.nosocialmedia.socialcounter.presentation.factory.NoSocialMediasStateFactory
import com.falon.nosocialmedia.socialcounter.presentation.mapper.NoSocialMediasViewStateMapper
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
    ): NoSocialMediaRepository =
        NoSocialMediasRepositoryImpl(
            NoSocialMediaDatabase(DatabaseDriverFactory(context).provide())
        )

    @Provides
    fun provideGetSocialMediaByIdUseCase(
        noSocialMediaRepository: NoSocialMediaRepository,
    ): GetSocialMediaByIdUseCase =
        GetSocialMediaByIdUseCase(noSocialMediaRepository = noSocialMediaRepository)

    @Provides
    fun provideIncreaseNoMediaCounterUseCase(
        getSocialMediaByIdUseCase: GetSocialMediaByIdUseCase,
        noSocialMediasCounterRepository: NoSocialMediaRepository,
    ): IncreaseNoMediaCounterUseCase = IncreaseNoMediaCounterUseCase(
        getSocialMediaByIdUseCase = getSocialMediaByIdUseCase,
        noSocialMediaRepository = noSocialMediasCounterRepository,
    )

    @Provides
    fun provideNoSocialMediasStateFactory(): NoSocialMediasStateFactory =
        NoSocialMediasStateFactory()

    @Provides
    fun provideNoSocialMediasViewStateMapper(): NoSocialMediasViewStateMapper =
        NoSocialMediasViewStateMapper()
}
