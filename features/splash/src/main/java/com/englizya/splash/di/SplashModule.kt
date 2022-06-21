package com.englizya.splash.di

import com.englizya.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    viewModel {
        SplashViewModel(get())
    }
}