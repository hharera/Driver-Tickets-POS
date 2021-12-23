package com.englyzia.datastore.di

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
        fun provideDriverDataStore(context: Context): DriverDataStore =
            DriverDataStore(context = context)

        @Provides
        @Singleton
        fun provideTicketDataStore(context: Context): TicketDataStore =
            TicketDataStore(context = context)

        @Provides
        @Singleton
        fun provideCarDataStore(context: Context): CarDataStore = CarDataStore(context = context)

        @Provides
        @Singleton
        fun provideCompanyDataStore(context: Context): CompanyDataStore =
            CompanyDataStore(context = context)

    }
}