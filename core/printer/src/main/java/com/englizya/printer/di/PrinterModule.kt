package com.englizya.printer.di

import android.app.Application
import com.pax.gl.page.PaxGLPage
import com.sunmi.peripheral.printer.InnerPrinterManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class PrinterModule {

    companion object {

        @Singleton
        @Provides
        fun providePrinter(): InnerPrinterManager =
            InnerPrinterManager.getInstance()

        @Singleton
        @Provides
        fun providePaxGl(application: Application): PaxGLPage =
            PaxGLPage.getInstance(application.applicationContext)
    }
}