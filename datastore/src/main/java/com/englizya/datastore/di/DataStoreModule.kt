package com.englizya.datastore.di

import android.app.Application
import com.englizya.datastore.core.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    companion object {

        @Provides
        @Singleton
        fun provideDriverDataStore(application: Application): DriverDataStore =
            DriverDataStore(context = application)

        @Provides
        @Singleton
        fun provideTicketDataStore(context: Application): TicketDataStore =
            TicketDataStore(context = context)

        @Provides
        @Singleton
        fun provideCarDataStore(context: Application): CarDataStore = CarDataStore(context = context)

        @Provides
        @Singleton
        fun provideCompanyDataStore(context: Application): CompanyDataStore =
            CompanyDataStore(context = context)

        @Provides
        @Singleton
        fun provideManifestoDataStore(context: Application): ManifestoDataStore =
            ManifestoDataStore(context = context)
    }
}