package com.englizya.xprinter_p300.di

import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.englizya.xprinter_p300.XPrinterP300
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PrinterModule {

    companion object {

        @Singleton
        @Provides
        fun bindPrinter(): EscPosPrinter {
            return EscPosPrinter(
                    BluetoothPrintersConnections.selectFirstPaired(),
                    203,
                    50f,
                    32
                )

        }
    }
}