package com.falon.nosocialmedia.socialcounter.domain.repository

interface NoSocialMediasCounterRepository {

    fun update(key: String, value: Int)

    fun getValue(key: String, defaultValue: Int): Int
}
