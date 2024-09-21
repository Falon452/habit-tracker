package com.falon.nosocialmedia.socialcounter.data

import android.content.Context
import com.falon.nosocialmedia.data.NoSocialMediaDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context,
) {

    actual fun provide(): SqlDriver {
        return AndroidSqliteDriver(
            NoSocialMediaDatabase.Schema,
            context,
            "nosocialmediadatabase.db"
        )
    }
}