package com.falon.habit.habits.data

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    fun provide(): SqlDriver
}
