package com.englizya.printer.di

import com.englizya.printer.TicketPrinter
import com.englizya.printer.TicketPrinterImpl
import com.pax.gl.page.PaxGLPage
import org.koin.dsl.module


val printerModule = module {

    single {
        PaxGLPage.getInstance(get())
    }

    single<TicketPrinter> {
        TicketPrinterImpl(get(), get(), get())
    }
}