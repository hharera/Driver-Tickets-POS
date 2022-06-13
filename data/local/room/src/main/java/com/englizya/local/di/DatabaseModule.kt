package com.englizya.local.di

import androidx.room.Room
import com.englizya.local.TicketDatabase
import com.englizya.local.utils.Constants.TICKET_DATA_BASE
import org.koin.dsl.module


val databaseModule = module {

    single {
        Room
            .databaseBuilder(
                get(),
                TicketDatabase::class.java, TICKET_DATA_BASE
            )
            .build()
    }

    single {
        get<TicketDatabase>().getTicketDao()
    }
}