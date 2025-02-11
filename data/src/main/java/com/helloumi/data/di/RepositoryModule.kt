package com.helloumi.data.di

import com.helloumi.data.api.apc.ApcAPI
import com.helloumi.data.repository.TodoRepositoryImpl
import com.helloumi.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideApcRepository(
        apcAPI: ApcAPI
    ): TodoRepository = TodoRepositoryImpl(apcAPI)
}
