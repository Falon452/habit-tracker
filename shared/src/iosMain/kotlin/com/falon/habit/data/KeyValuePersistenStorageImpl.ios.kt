package com.falon.habit.data

import com.falon.habit.domain.contract.KeyValuePersistentStorage

actual class KeyValuePersistentStorageImpl : KeyValuePersistentStorage {

    override fun update(key: String, value: Int) {
        TODO("Not yet implemented")
    }

    override fun get(key: String, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }
}