package com.englizya.printer

import android.app.Application
import android.graphics.Bitmap
import com.sunmi.peripheral.printer.InnerPrinterCallback
import com.sunmi.peripheral.printer.InnerPrinterManager
import com.sunmi.peripheral.printer.InnerResultCallback
import com.sunmi.peripheral.printer.SunmiPrinterService
import javax.inject.Inject


class SunmiPrinter @Inject constructor(
    private val printerManager: InnerPrinterManager,
    private val application: Application,
) {

    fun print(bitmap: Bitmap) {
        printerManager.bindService(application, object : InnerPrinterCallback() {
            override fun onConnected(printerService: SunmiPrinterService) {
                printerService.printBitmap(bitmap, object : InnerResultCallback() {
                    override fun onRunResult(p0: Boolean) {
                    }

                    override fun onReturnString(p0: String?) {
                    }

                    override fun onRaiseException(p0: Int, p1: String?) {
                    }

                    override fun onPrintResult(p0: Int, p1: String?) {
                    }

                })
            }

            override fun onDisconnected() {
            }
        })
    }
}