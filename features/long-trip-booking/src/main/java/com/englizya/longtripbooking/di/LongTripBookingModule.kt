package com.englizya.longtripbooking.di

import com.englizya.longtripbooking.LongTripBookingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val longTripBookingModule = module {
    viewModel {
        LongTripBookingViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}