package com.falon.habit.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context,
) {

    actual fun provide(): SqlDriver {
        return AndroidSqliteDriver(
            HabitDatabase.Schema,
            context,
            "habitdatabase.db"
        )
    }
}