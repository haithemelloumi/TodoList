package com.helloumi.data.di

import com.helloumi.data.database.TodoDatabase
import com.helloumi.data.datasource.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao =
        database.todoDao()
}
