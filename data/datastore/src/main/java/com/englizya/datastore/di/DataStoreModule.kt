package com.englizya.datastore.di

import com.englizya.datastore.LocalTicketPreferences
import org.koin.dsl.module


val dataStoreModule = module {
    single { LocalTicketPreferences(get()) }
}