package com.englizya.api.di

import com.englizya.api.utils.Request
import com.englizya.datastore.core.DriverDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.http.auth.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ClientModule {

    companion object {

        @Provides
        @Singleton
        fun provideClient(): HttpClient =
            HttpClient(Android) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = Request.TIME_OUT
                }
            }
    }
}