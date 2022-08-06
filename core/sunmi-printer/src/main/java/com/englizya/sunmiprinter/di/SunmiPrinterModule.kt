package com.englizya.sunmiprinter.di

import com.englizya.sunmiprinter.SunmiPrinter
import com.sunmi.peripheral.printer.InnerPrinterManager
import org.koin.dsl.module

val sunmiPrinterModule = module {

    single {
        InnerPrinterManager.getInstance()
    }

    single {
        SunmiPrinter(get(), get())
    }
}