package com.englizya.booking_report.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.englizya.booking_report.BookingReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookingReportModule = module {
    viewModel{
        BookingReportViewModel(get(),get())
    }
}