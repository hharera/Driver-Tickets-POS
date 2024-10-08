package com.englizya.end_shift.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.englizya.end_shift.EndShiftViewModel
import com.englizya.end_shift.LongManifestoEndShiftViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val endShiftModule = module {
    viewModel {
        EndShiftViewModel(get(),get(),get(),get())
    }
}

val longManifestoEndShiftModule = module {
    viewModel {
        LongManifestoEndShiftViewModel(get(),get(),get(),get())
    }
}