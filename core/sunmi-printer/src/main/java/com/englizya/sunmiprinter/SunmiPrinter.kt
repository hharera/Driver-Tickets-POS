package com.englizya.sunmiprinter

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.sunmi.peripheral.printer.InnerPrinterCallback
import com.sunmi.peripheral.printer.InnerPrinterManager
import com.sunmi.peripheral.printer.InnerResultCallback
import com.sunmi.peripheral.printer.SunmiPrinterService


class SunmiPrinter constructor(
    private val printerManager: InnerPrinterManager,
    private val application: Application,
) {

    companion object {
        private const val TAG = "SunmiPrinter"
    }

    fun print(bitmap: Bitmap) {
        printerManager.bindService(application, object : InnerPrinterCallback() {
            override fun onConnected(printerService: SunmiPrinterService) {
                printerService.printBitmap(bitmap, object : InnerResultCallback() {
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

                })
            }

            override fun onDisconnected() {
                Log.d(TAG, "onDisconnected: ")
            }
        })
    }
}