package com.example.scan_reserved_ticket.di

import com.example.scan_reserved_ticket.ScanReservedTicketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val scanReservedTicketModule = module {
    viewModel {
        ScanReservedTicketViewModel(
            get(),
            get(),
            get()

        )
    }
}