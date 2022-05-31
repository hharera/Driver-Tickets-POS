package com.englizya.api.di

import com.englizya.api.*
import com.englizya.api.impl.RemoteManifestoServiceImpl
import com.englizya.api.impl.RemoteTicketServiceImpl
import com.englizya.api.impl.RemoteUserServiceImpl


val remoteModule = module {

    single {
        RemoteUserServiceImpl(get())
    }

    signle<RemoteManifestoService> {
        RemoteManifestoServiceImpl
    }

    single {
        RemoteTicketServiceImpl
    }

    single {

        WalletServiceImpl
    }
    single {
        TripServiceImpl
    }
}