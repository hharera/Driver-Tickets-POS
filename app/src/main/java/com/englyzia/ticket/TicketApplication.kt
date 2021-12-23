package com.englyzia.ticket

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TicketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}