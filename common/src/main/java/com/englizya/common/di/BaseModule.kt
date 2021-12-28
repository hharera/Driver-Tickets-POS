package com.englizya.common.di

import android.app.Application
import com.englizya.common.utils.network.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class BaseModule {

    companion object {

        @Provides
        @Singleton
        fun bindConnectionLiveData(application: Application): ConnectionLiveData {
            return ConnectionLiveData(application)
        }
    }


}