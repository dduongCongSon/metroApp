package org.com.metro.di

import org.com.metro.repositories.Api
import org.com.metro.repositories.ApiImpl
import org.com.metro.repositories.MainLog
import org.com.metro.repositories.MainLogImpl
import org.com.metro.repositories.Store
import org.com.metro.repositories.StoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds //link to a specific implementation of the interface
    @Singleton
    abstract fun bindMainLog(
        log: MainLogImpl
    ): MainLog

    @Binds
    @Singleton
    abstract fun bindApi(
        api: ApiImpl
    ): Api

    @Binds
    @Singleton
    abstract fun bindStore(store: StoreImpl): Store
}