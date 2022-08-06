package com.englizya.sunmiprinter

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.sunmi.peripheral.printer.*


class SunmiPrinter constructor(
    private val printerManager: InnerPrinterManager,
    private val application: Application,
) {

    companion object {
        private const val TAG = "SunmiPrinter"
    }

    private var printerService: SunmiPrinterService? = null

    init {
        bindPrinter()
    }

    private fun bindPrinter() {
        printerManager.bindService(application, object : InnerPrinterCallback() {
            override fun onConnected(printerService: SunmiPrinterService) {
                this@SunmiPrinter.printerService = printerService
                printerService.clearBuffer()
            }

            override fun onDisconnected() {
                Log.d(TAG, "onDisconnected: ")
            }
        })
    }


    fun print(bitmap: Bitmap) {
        if (printerService == null) {
            Toast.makeText(application, "Printer not connected", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "print: printer service is null")
            return
        }
        printerService?.clearBuffer()
        printerService?.enterPrinterBuffer(true)
        printerService?.printBitmap(bitmap, callback)
        printerService?.printText("\n", callback)
        printerService?.commitPrinterBuffer()
        printerService?.exitPrinterBuffer(true)
    }

    private val callback = object : InnerResultCallback() {
        override fun onRunResult(p0: Boolean) {
            Log.d(TAG, "onRunResult: $p0")
        }

        override fun onReturnString(p0: String?) {
            Log.d(TAG, "onReturnString: $p0")
        }

        override fun onRaiseException(p0: Int, p1: String?) {
            Log.d(TAG, "onRaiseException $p0  $p1")
            Toast.makeText(
                application,
                application.getString(R.string.printing_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onPrintResult(p0: Int, p1: String?) {
            Log.d(TAG, "onPrintResult: $p0  $p1")
        }
    }
}