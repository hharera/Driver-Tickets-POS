package com.englizya.ticket.di

import com.englizya.ticket.TicketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ticketModule = module {
    viewModel {
        TicketViewModel(
            get(),
            get(),
            get(),
            get(),
        )
    }
}