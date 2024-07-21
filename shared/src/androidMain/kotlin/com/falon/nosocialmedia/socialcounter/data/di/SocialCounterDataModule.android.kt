package com.falon.nosocialmedia.socialcounter.data.di

import com.falon.nosocialmedia.socialcounter.data.repository.NoSocialMediasCounterRepositoryImpl
import com.falon.nosocialmedia.socialcounter.data.sources.KeyValuePersistentStorageImpl
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediasCounterRepository

actual object SocialCounterDataModule {

    actual fun provideNoSocialMediasCounterRepository(storage: KeyValuePersistentStorageImpl): NoSocialMediasCounterRepository {
        return NoSocialMediasCounterRepositoryImpl(
            keyValuePersistentStorage = storage,
        )
    }
}
