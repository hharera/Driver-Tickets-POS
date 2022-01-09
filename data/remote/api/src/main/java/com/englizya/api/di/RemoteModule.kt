package com.englizya.api.di

import com.englizya.api.RemoteManifestoService
import com.englizya.api.RemoteTicketService
import com.englizya.api.RemoteUserService
import com.englizya.api.impl.RemoteManifestoServiceImpl
import com.englizya.api.impl.RemoteTicketServiceImpl
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
    abstract fun provideRemoteUserService(remoteUserServiceImpl: RemoteUserServiceImpl): RemoteUserService

    @Singleton
    @Binds
    abstract fun provideRemoteManifestoService(remoteManifestoServiceImpl: RemoteManifestoServiceImpl): RemoteManifestoService

    @Singleton
    @Binds
    abstract fun provideRemoteTicketService(remoteTicketServiceImpl: RemoteTicketServiceImpl): RemoteTicketService
}