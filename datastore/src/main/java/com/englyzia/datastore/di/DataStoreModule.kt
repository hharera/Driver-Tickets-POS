package com.englyzia.datastore.di

import android.app.Application
import android.content.Context
import com.englyzia.datastore.core.CarDataStore
import com.englyzia.datastore.core.CompanyDataStore
import com.englyzia.datastore.core.DriverDataStore
import com.englyzia.datastore.core.TicketDataStore
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

    }
}