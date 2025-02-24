package com.falon.habit.habits.data

import android.content.Context
import com.falon.habit.data.HabitDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context,
) {

    actual fun provide(): SqlDriver {
        return AndroidSqliteDriver(
            HabitDatabase.Companion.Schema,
            context,
            "habitdatabase.db"
        )
    }
}
