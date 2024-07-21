package com.falon.nosocialmedia.socialcounter.data.repository

import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediasCounterRepository

class NoSocialMediasCounterRepositoryImpl(
    private val keyValuePersistentStorage: KeyValuePersistentStorage,
): NoSocialMediasCounterRepository {

    override fun update(key: String, value: Int) {
        keyValuePersistentStorage.update(key, value)
    }

    override fun getValue(key: String, defaultValue: Int): Int =
        keyValuePersistentStorage.get(key, defaultValue)
}
