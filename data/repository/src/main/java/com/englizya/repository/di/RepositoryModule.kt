package com.englizya.repository.di

import com.englizya.repository.*
import com.englizya.repository.impl.ManifestoRepositoryImpl
import com.englizya.repository.impl.StationRepositoryImpl
import com.englizya.repository.impl.TicketRepositoryImpl
import com.englizya.repository.impl.UserRepositoryImpl
import org.koin.dsl.module


val repositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(get()) }

    single<ManifestoRepository> { ManifestoRepositoryImpl(get()) }

    single<TicketRepository> { TicketRepositoryImpl(get(), get()) }

    single<WalletRepository> {
        WalletRepositoryImpl(get())
    }

    single<StationRepository> {
        StationRepositoryImpl(get())
    }
}
