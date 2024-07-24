package com.falon.nosocialmedia.socialcounter.data.repository

import com.falon.nosocialmedia.core.domain.flow.CommonFlow
import com.falon.nosocialmedia.core.domain.flow.toCommonFlow
import com.falon.nosocialmedia.data.NoSocialMediaDatabase
import com.falon.nosocialmedia.socialcounter.domain.model.NoSocialCounter
import com.falon.nosocialmedia.socialcounter.domain.repository.NoSocialMediaRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList

import kotlinx.coroutines.flow.map

class NoSocialMediasRepositoryImpl(
    private val db: NoSocialMediaDatabase
): NoSocialMediaRepository {

    private val queries = db.socialmediasQueries

    override fun observeSocialMedias(): CommonFlow<List<NoSocialCounter>> {
        return queries.getSocialMedias().asFlow().mapToList().map {
            socialMediaEntities ->
            socialMediaEntities.map {
                NoSocialCounter(
                    it.id.toInt(),
                    it.daysCount.toInt(),
                    it.name,
                )
            }
        }.toCommonFlow()
    }

    override fun insertSocialMedias(noSocialCounter: NoSocialCounter) {
        queries.insertSocialMediaEntity(noSocialCounter.id.toLong(), noSocialCounter.name, noSocialCounter.numberOfDays.toLong())
    }
}