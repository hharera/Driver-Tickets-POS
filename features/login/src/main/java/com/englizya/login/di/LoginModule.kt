package com.englizya.login.di

import com.englizya.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val loginModule = module {
    viewModel {
        LoginViewModel(
            get(),
            get(),
            get(),
        )
    }
}