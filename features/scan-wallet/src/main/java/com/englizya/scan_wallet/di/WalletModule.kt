package com.englizya.scan_wallet.di

import com.englizya.scan_wallet.WalletPaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val walletModule = module {
    viewModel {
        WalletPaymentViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}