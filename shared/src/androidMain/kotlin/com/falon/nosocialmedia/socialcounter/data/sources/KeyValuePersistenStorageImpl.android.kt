package com.falon.nosocialmedia.socialcounter.data.sources

import android.content.SharedPreferences
import com.falon.nosocialmedia.socialcounter.data.repository.KeyValuePersistentStorage

actual class KeyValuePersistentStorageImpl(
    private val sharedPreferences: SharedPreferences,
) : KeyValuePersistentStorage {

    override fun update(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun get(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)
}