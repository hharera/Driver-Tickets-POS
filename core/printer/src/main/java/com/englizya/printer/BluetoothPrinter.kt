//package com.englizya.printer
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.dantsu.escposprinter.EscPosPrinter
//import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
//import com.dantsu.escposprinter.textparser.PrinterTextParserImg
//
//
//class BluetoothPrinter : AppCompatActivity() {
//    val PERMISSION_BLUETOOTH = 1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.BLUETOOTH
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.BLUETOOTH),
//                PERMISSION_BLUETOOTH
//            );
//        } else {
//            print()
//        }
//    }
//
//    private fun print() {
//        val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
//        printer.printFormattedText(
//            """
//        [C]<img>${
//                PrinterTextParserImg.bitmapToHexadecimalString(
//                    printer,
//                    getDrawable(com.englizya.common.R.drawable.logo)
//                )
//            }</img>
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
//        )
//    }
//
//}