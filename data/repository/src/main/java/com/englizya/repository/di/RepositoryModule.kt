package com.englizya.repository.di

import com.englizya.repository.*
import com.englizya.repository.impl.*
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
    single<SeatPricingRepository> {
        SeatPricingRepositoryImpl(get())
    }
    single<BookingInformationRepository> {
        BookingInformationRepositoryImpl(get())
    }
}
