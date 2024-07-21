package com.falon.nosocialmedia.socialcounter.data.di

import com.falon.nosocialmedia.socialcounter.data.sources.KeyValuePersistentStorageImpl
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediasCounterRepository

expect object SocialCounterDataModule {

    fun provideNoSocialMediasCounterRepository(storage: KeyValuePersistentStorageImpl): NoSocialMediasCounterRepository
}
