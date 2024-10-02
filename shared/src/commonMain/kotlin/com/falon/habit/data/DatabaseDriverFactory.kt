package com.falon.habit.data

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    fun provide(): SqlDriver
}