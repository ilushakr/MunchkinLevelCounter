package com.example.munchkinlevelcounter.cache

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory() {
    fun createDriver(schema: SqlDriver.Schema, name: String): SqlDriver
}
