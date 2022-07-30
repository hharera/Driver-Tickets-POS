package com.englizya.sunmiprinter.di

import com.englizya.sunmiprinter.SunmiPrinter
import com.sunmi.peripheral.printer.InnerPrinterManager
import org.koin.dsl.module

val sunmiPrinterModule = module {
    single {
        SunmiPrinter(InnerPrinterManager.getInstance(), get())
    }
}