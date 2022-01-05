package com.englizya.printer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class PrinterModule {

    companion object {

        @Provides
        fun providePrinter(): TicketPrinter {

        }

    }


}