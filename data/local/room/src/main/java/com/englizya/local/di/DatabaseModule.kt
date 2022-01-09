package com.englizya.local.di

import android.app.Application
import androidx.room.Room
import com.englizya.local.TicketDao
import com.englizya.local.TicketDatabase
import com.englizya.local.utils.Constants.TICKET_DATA_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): TicketDatabase =
        Room.databaseBuilder(application, TicketDatabase::class.java, TICKET_DATA_BASE).build()

    @Provides
    @Singleton
    fun provideDao(database: TicketDatabase): TicketDao =
        database.getMarketDao()
}