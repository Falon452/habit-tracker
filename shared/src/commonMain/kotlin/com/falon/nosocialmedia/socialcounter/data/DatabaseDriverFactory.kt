package com.falon.nosocialmedia.socialcounter.data

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    fun provide(): SqlDriver
}