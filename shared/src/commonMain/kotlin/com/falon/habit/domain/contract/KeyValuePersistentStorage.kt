package com.falon.habit.domain.contract

interface KeyValuePersistentStorage {

    fun update(key: String, value: Int)

    fun get(key: String, defaultValue: Int): Int
}
