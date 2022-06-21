package com.englizya.ticket

import android.app.Application
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.common.di.baseModule
import com.englizya.datastore.di.dataStoreModule
import com.englizya.end_shift.di.endShiftModule
import com.englizya.local.di.databaseModule
import com.englizya.login.di.loginModule
import com.englizya.printer.di.printerModule
import com.englizya.repository.di.repositoryModule
import com.englizya.splash.di.splashModule
import com.englizya.ticket.di.ticketModule
import com.englizya.wallet.di.walletModule
import com.englizya.xprinter_p300.di.xprinterModule
import com.example.scan_reserved_ticket.di.scanReservedTicketModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class TicketApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@TicketApplication)
            modules(
                baseModule,
                xprinterModule,
                printerModule,
                dataStoreModule,
                databaseModule,
                clientModule,
                remoteModule,
                repositoryModule,
                splashModule,
                walletModule,
                ticketModule,
                scanReservedTicketModule,
                loginModule,
                endShiftModule,
            )
        }
    }
}