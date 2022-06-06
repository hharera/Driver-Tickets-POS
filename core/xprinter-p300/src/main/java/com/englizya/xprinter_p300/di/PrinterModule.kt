package com.englizya.xprinter_p300.di

import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import org.koin.dsl.module


val printerModule = module {
    single {
        EscPosPrinter(
            BluetoothPrintersConnections.selectFirstPaired(),
            203,
            50f,
            32
        )
    }
}
