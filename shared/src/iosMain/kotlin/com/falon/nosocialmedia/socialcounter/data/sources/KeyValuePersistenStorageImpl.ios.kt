package com.falon.nosocialmedia.socialcounter.data.sources

import com.falon.nosocialmedia.socialcounter.data.repository.KeyValuePersistentStorage

actual class KeyValuePersistentStorageImpl : KeyValuePersistentStorage {

    override fun update(key: String, value: Int) {
        TODO("Not yet implemented")
    }

    override fun get(key: String, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }
}
