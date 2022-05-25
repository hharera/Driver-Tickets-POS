package com.englizya.xprinter_p300

import android.graphics.Bitmap
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.Ticket
import com.englizya.xprinter_p300.ArabicParameters.CAR_CODE
import com.englizya.xprinter_p300.ArabicParameters.DRIVER_CODE
import com.englizya.xprinter_p300.ArabicParameters.LINE_CODE


object XPrinterP300 {

    fun print(ticket: Ticket, logo: Bitmap) {
        val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
        printer
            .printFormattedText(
                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                    printer,
                    logo
                ) + "</img>\n" +
                        "[L]\n" +
                        "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                        "[L]  + Size : S\n" +
                        "[L]\n" +
                        "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                        "[L]  + Size : 57/58\n" +
                        "[L]\n" +
                        "[C]--------------------------------\n" +
                        "[R]TOTAL PRICE :[R]34.98e\n" +
                        "[R]TAX :[R]4.23e\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<font size='tall'>Customer :</font>\n" +
                        "[L]Raymond DUPONT\n" +
                        "[L]5 rue des girafes\n" +
                        "[L]31547 PERPETES\n" +
                        "[L]Tel : +33801201456\n" +
                        "[L]\n" +
                        "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                        "[C]<qrcode size='20'>http://www.developpeur-web.dantsu.com/</qrcode>"
            )

    }

    fun print(bitmap: Bitmap) {
        EscPosPrinter(
            BluetoothPrintersConnections.selectFirstPaired(),
            203,
            48f,
            32
        ).apply {
            printFormattedTextAndCut(
                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                    this,
                    bitmap
                ) + "</img>\n"
            )
        }
    }

    fun print(
        ticket: com.englizya.model.request.Ticket,
        logo: Bitmap,
        category: Bitmap,
        tele: Bitmap,
        page: Bitmap,
    ) {
        val escPosPrinter =
            EscPosPrinter(
                BluetoothPrintersConnections.selectFirstPaired(),
                203,
                50f,
                32
            )

        val logoHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            logo
        )

        val catHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            category
        )

        val teleHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            tele
        )

        val pageHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            page
        )

        escPosPrinter.printFormattedTextAndCut(
            "[C]<img>$logoHex</img>\n" +
                    "[L]\n" +
                    "[C]<img>$pageHex</img>\n" +
                    "[C]<img>$catHex</img>\n" +
                    "[C]<img>$teleHex</img>\n"
        )
    }
}
