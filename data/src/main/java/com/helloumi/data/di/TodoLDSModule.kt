package com.helloumi.data.di

import com.helloumi.data.database.datasources.interfaces.TodoLDS
import com.helloumi.data.datasource.TodoDao
import com.helloumi.data.local.datasource.TodoLDSImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoLDSModule {

    @Provides
    @Singleton
    fun provideTodoLDS(
        todoDao: TodoDao
    ): TodoLDS = TodoLDSImpl(todoDao)
}
