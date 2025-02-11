package com.helloumi.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.helloumi.data.database.TodoDatabase.Companion.DATABASE_NAME
import javax.inject.Inject

class TodoDatabaseFactoryImpl @Inject constructor() : TodoDatabaseFactory {
    override fun build(context: Context, bddFile: String, vararg migrations: Migration) =
        Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build() //The reason we can construct a database from the repo
}
