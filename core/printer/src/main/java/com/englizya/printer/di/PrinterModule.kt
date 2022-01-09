package com.englizya.printer.di

import android.app.Application
import com.englizya.printer.PaxPrinter
import com.englizya.printer.TicketPrinter
import com.pax.dal.IDAL
import com.pax.dal.IPrinter
import com.pax.neptunelite.api.NeptuneLiteUser
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
        fun providePrinter(printer: IPrinter): PaxPrinter =
            PaxPrinter(printer)

        @Singleton
        @Provides
        fun provideIDAL(application: Application): IDAL =
            NeptuneLiteUser.getInstance().getDal(application)

        @Singleton
        @Provides
        fun provideBasePrinter(idal: IDAL): IPrinter = idal.printer

        @Singleton
        @Provides
        fun provideTicketPrinter(paxPrinter: PaxPrinter): TicketPrinter = TicketPrinter()
    }
}