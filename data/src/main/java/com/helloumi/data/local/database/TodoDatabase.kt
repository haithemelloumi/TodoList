package com.helloumi.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.helloumi.data.database.TodoDatabase.Companion.DATABASE_VERSION
import com.helloumi.data.datasource.TodoDao
import com.helloumi.data.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)


abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "todo_database.db"

        const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        @JvmStatic
        fun getInstance(context: Context, factory: TodoDatabaseFactory): TodoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, factory).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context, factory: TodoDatabaseFactory) =
            factory.build(
                context,
                DATABASE_NAME,
            )
    }
}
