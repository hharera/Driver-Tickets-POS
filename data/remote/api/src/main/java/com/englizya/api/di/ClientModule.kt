package com.englizya.api.di

import com.englizya.api.utils.Request
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import org.koin.dsl.module


val clientModule = module {

    single {
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