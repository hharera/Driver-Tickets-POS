package com.englizya.api.di

import com.englizya.api.RemoteUserService
import com.englizya.api.impl.RemoteUserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Singleton
    @Binds
    abstract fun provideUserService(remoteUserServiceImpl: RemoteUserServiceImpl): RemoteUserService
}