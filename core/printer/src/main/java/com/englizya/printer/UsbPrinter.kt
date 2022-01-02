//package com.englizya.printer
//
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.hardware.usb.UsbDevice
//import android.hardware.usb.UsbManager
//import android.os.Bundle
//import android.os.Parcelable
//import androidx.appcompat.app.AppCompatActivity
//import com.englizya.common.R
//import com.dantsu.escposprinter.connection.usb.UsbConnection
//import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections
//
//import com.dantsu.escposprinter.textparser.PrinterTextParserImg
//
//import com.dantsu.escposprinter.EscPosPrinter
//
//
//class UsbPrinter() : AppCompatActivity() {
//
//    private val PERMISSION_BLUETOOTH = 1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        printUsb()
//    }
//
//    private val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
//    private val usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent) {
//            val action = intent.action
//            if (ACTION_USB_PERMISSION.equals(action)) {
//                synchronized(this) {
//                    val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager?
//                    val usbDevice =
//                        intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice?
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if (usbManager != null && usbDevice != null) {
//                            val printer =
//                                EscPosPrinter(UsbConnection(usbManager, usbDevice), 203, 48f, 32)
//                            printer.printFormattedText(
//                                """
//        [C]<img>${
//                                    PrinterTextParserImg.bitmapToHexadecimalString(
//                                        printer,
//                                        getDrawable(R.drawable.logo)
//                                    )
//                                }</img>
//        [L]
//        [C]<u><font size='big'>ORDER N°045</font></u>
//        [L]
//        [C]================================
//        [L]
//        [L]<b>BEAUTIFUL SHIRT</b>[R]9.99e
//        [L]  + Size : S
//        [L]
//        [L]<b>AWESOME HAT</b>[R]24.99e
//        [L]  + Size : 57/58
//        [L]
//        [C]--------------------------------
//        [R]TOTAL PRICE :[R]34.98e
//        [R]TAX :[R]4.23e
//        [L]
//        [C]================================
//        [L]
//        [L]<font size='tall'>Customer :</font>
//        [L]Raymond DUPONT
//        [L]5 rue des girafes
//        [L]31547 PERPETES
//        [L]Tel : +33801201456
//        [L]
//        [C]<barcode type='ean13' height='10'>831254784551</barcode>
//        [C]<qrcode size='20'>http://www.developpeur-web.dantsu.com/</qrcode>
//        """.trimIndent()
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun printUsb() {
//        val usbConnection: UsbConnection = UsbPrintersConnections.selectFirstConnected(this)!!
//        val usbManager = this.getSystemService(Context.USB_SERVICE) as UsbManager
//        if (usbConnection != null && usbManager != null) {
//            val permissionIntent =
//                PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSION), 0)
//            val filter = IntentFilter(ACTION_USB_PERMISSION)
//            registerReceiver(this.usbReceiver, filter)
//            usbManager.requestPermission(usbConnection.getDevice(), permissionIntent)
//        }
//    }
//}
