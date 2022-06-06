package com.englizya.common.di

import com.englizya.common.utils.network.ConnectionLiveData
import org.koin.dsl.module


val baseModule = module {
    single { ConnectionLiveData(get()) }
}