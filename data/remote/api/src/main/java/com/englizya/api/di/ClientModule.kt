package com.englizya.api.di

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

                this.defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
    }
}