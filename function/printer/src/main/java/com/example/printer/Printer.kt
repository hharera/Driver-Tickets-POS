package com.example.printer

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Parcelable


class Printer {

    private const val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
    private val usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (MainActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized(this) {
                    val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager?
                    val usbDevice =
                        intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice?
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {
                            // YOUR PRINT CODE HERE
                        }
                    }
                }
            }
        }
    }

    fun printUsb() {
        val usbConnection: UsbConnection = UsbPrintersConnections.selectFirstConnected(this)
        val usbManager = this.getSystemService(Context.USB_SERVICE) as UsbManager
        if (usbConnection != null && usbManager != null) {
            val permissionIntent =
                PendingIntent.getBroadcast(this, 0, Intent(MainActivity.ACTION_USB_PERMISSION), 0)
            val filter = IntentFilter(MainActivity.ACTION_USB_PERMISSION)
            registerReceiver(this.usbReceiver, filter)
            usbManager.requestPermission(usbConnection.getDevice(), permissionIntent)
        }
    }


}
