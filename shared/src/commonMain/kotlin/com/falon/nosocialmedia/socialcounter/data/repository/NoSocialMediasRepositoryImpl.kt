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
    private val db: NoSocialMediaDatabase,
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

    override fun getSocialMedia(id: Int): NoSocialCounter? {
        val entity = queries.getSocialMedias().executeAsOneOrNull()
        return if (entity != null) {
            NoSocialCounter(
                entity.id.toInt(),
                entity.daysCount.toInt(),
                entity.name,
            )
        } else {
            null
        }
    }

    override fun initializeDatabase() {
        val existingItems = queries.getSocialMedias().executeAsList()
        if (existingItems.isEmpty()) {
            val predefinedItems = listOf(
                NoSocialCounter(1, 12, "Instagram"),
                NoSocialCounter(2, 12, "Facebook"),
                // Add the rest of your predefined data here...
            )
            predefinedItems.forEach { insertSocialMedias(it) }
        }
    }
}