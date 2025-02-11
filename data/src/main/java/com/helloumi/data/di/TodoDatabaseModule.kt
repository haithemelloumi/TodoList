package com.helloumi.data.di

import android.content.Context
import com.helloumi.data.database.TodoDatabase
import com.helloumi.data.database.TodoDatabaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDatabaseModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context, factory: TodoDatabaseFactory): TodoDatabase {
        return TodoDatabase.getInstance(context, factory)
    }
}
