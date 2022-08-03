package com.englizya.ticket

import android.app.Application
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.common.di.baseModule
import com.englizya.datastore.di.dataStoreModule
import com.englizya.end_shift.di.endShiftModule
import com.englizya.end_shift.di.longManifestoEndShiftModule
import com.englizya.local.di.databaseModule
import com.englizya.login.di.loginModule
import com.englizya.longtripbooking.di.longTripBookingModule
import com.englizya.printer.di.printerModule
import com.englizya.repository.di.repositoryModule
import com.englizya.scan_wallet.di.walletModule
import com.englizya.splash.di.splashModule
import com.englizya.sunmiprinter.di.sunmiPrinterModule
import com.englizya.ticket.di.ticketModule
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
                sunmiPrinterModule,
                printerModule,
                longTripBookingModule,
                dataStoreModule,
                databaseModule,
                clientModule,
                remoteModule,
                repositoryModule,
                splashModule,
                ticketModule,
                scanReservedTicketModule,
                walletModule,
                loginModule,
                endShiftModule,
                longManifestoEndShiftModule,
            )
        }
    }
}