package com.falon.nosocialmedia.socialcounter.data

import com.falon.nosocialmedia.data.NoSocialMediaDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun provide(): SqlDriver {
        return NativeSqliteDriver(NoSocialMediaDatabase.Schema, "nosocialmedia.db")
    }

}