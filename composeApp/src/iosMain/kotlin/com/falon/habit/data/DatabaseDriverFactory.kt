package com.falon.habit.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun provide(): SqlDriver {
        return NativeSqliteDriver(HabitDatabase.Schema, "habit.db")
    }
}
