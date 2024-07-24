package com.falon.nosocialmedia.socialcounter.data.repository

interface KeyValuePersistentStorage {

    fun update(key: String, value: Int)

    fun get(key: String, defaultValue: Int): Int
}