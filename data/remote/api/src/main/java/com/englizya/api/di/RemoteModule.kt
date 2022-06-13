package com.englizya.api.di

import com.englizya.api.*
import com.englizya.api.impl.RemoteManifestoServiceImpl
import com.englizya.api.impl.RemoteTicketServiceImpl
import com.englizya.api.impl.RemoteUserServiceImpl
import com.englizya.api.impl.StationServiceImpl
import org.koin.dsl.module


val remoteModule = module {

    single<RemoteUserService> {
        RemoteUserServiceImpl(get())
    }

    single<RemoteTicketService> {
        RemoteTicketServiceImpl(get(), get())
    }

    single<RemoteManifestoService> {
        RemoteManifestoServiceImpl(get(), get())
    }

    single<StationService> {
        StationServiceImpl(get())
    }

    single<WalletService> {
        WalletServiceImpl(get())
    }

    single<TripService> {
        TripServiceImpl(get())
    }
}
