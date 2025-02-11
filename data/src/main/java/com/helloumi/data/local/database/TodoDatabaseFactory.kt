package com.helloumi.data.database

import android.content.Context
import androidx.room.migration.Migration

interface TodoDatabaseFactory {
    fun build(context: Context, bddFile: String, vararg migrations: Migration): TodoDatabase
}
