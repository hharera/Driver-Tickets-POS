package com.englizya.ticket

import android.app.Application
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

            )
        }
    }
}