package com.helloumi.data.di

import com.helloumi.data.database.TodoDatabaseFactory
import com.helloumi.data.database.TodoDatabaseFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoDatabaseFactoryModule {

    @Singleton
    @Binds
    abstract fun bindTodoDatabaseFactory(factory: TodoDatabaseFactoryImpl): TodoDatabaseFactory
}
