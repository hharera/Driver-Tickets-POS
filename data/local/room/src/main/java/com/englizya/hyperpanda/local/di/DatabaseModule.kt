//package com.englizya.hyperpanda.local.di
//
//import android.app.Application
//import androidx.room.Room
//import com.englizya.hyperpanda.local.MarketDao
//import com.englizya.hyperpanda.local.MarketDatabase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//
//@Module
//@InstallIn(SingletonComponent::class)
//class DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun bindMarketDatabase(application: Application): MarketDatabase =
//        Room
//            .databaseBuilder(
//                application,
//                MarketDatabase::class.java,
//                "Market"
//            )
//            .build()
//
//    @Provides
//    @Singleton
//    fun bindMarketDao(database: MarketDatabase): MarketDao =
//        database.getMarketDao()
//}