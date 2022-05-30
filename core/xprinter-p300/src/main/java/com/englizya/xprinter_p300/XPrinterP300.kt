package com.englizya.xprinter_p300

import android.graphics.Bitmap
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.xprinter_p300.ArabicParameters.CARD_TICKETS
import com.englizya.xprinter_p300.ArabicParameters.CAR_CODE
import com.englizya.xprinter_p300.ArabicParameters.CASH_TICKETS
import com.englizya.xprinter_p300.ArabicParameters.DRIVER_CODE
import com.englizya.xprinter_p300.ArabicParameters.LINE_CODE
import com.englizya.xprinter_p300.ArabicParameters.MANIFESTO_DATE
import com.englizya.xprinter_p300.ArabicParameters.QR_TICKETS
import com.englizya.xprinter_p300.ArabicParameters.SHIFT_END
import com.englizya.xprinter_p300.ArabicParameters.SHIFT_START
import com.englizya.xprinter_p300.ArabicParameters.TOTAL_INCOME
import com.englizya.xprinter_p300.ArabicParameters.TOTAL_TICKETS
import com.englizya.xprinter_p300.ArabicParameters.WORK_HOURS
import java.nio.charset.Charset


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
        ticket: Ticket,
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
                    "[C]<img>$pageHex</img>\n" +
                    "[C]${TimeUtils.getDate(ticket.time)}\n" +
                    "[C]${TimeUtils.getTime(ticket.time)}\n" +
                    "[C]<img>$catHex</img>\n" +
                    "[C]<img>$teleHex</img>\n"
        )
    }

    fun print(
        logo: Bitmap,
        endShiftReportResponse: ShiftReportResponse
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


//        val bytesToHexadecimalString =
//            PrinterTextParserImg.bytesToHexadecimalString("${endShiftReportResponse.carCode}$CAR_CODE\n".toByteArray())
//        escPosPrinter.printFormattedTextAndCut("[C]<img>$bytesToHexadecimalString</img>\n")

        escPosPrinter
            .printFormattedTextAndCut(
                "[C]<img>$logoHex</img>\n" +
                        "[L]${endShiftReportResponse.driverCode}[R]$DRIVER_CODE\n" +
                        "[L]${endShiftReportResponse.carCode}[R]$CAR_CODE\n" +
                        "[L]${endShiftReportResponse.lineCode}[R]$LINE_CODE\n" +
                        "[L]${endShiftReportResponse.date}[R]$MANIFESTO_DATE\n" +
                        "[L]${endShiftReportResponse.startTime}[R]$SHIFT_START\n" +
                        "[L]${endShiftReportResponse.endTime}[R]$SHIFT_END\n" +
                        "[L]${TimeUtils.calculateWorkHours(endShiftReportResponse)}[R]$WORK_HOURS\n" +
                        "[L]${endShiftReportResponse.cash}[R]$CASH_TICKETS\n" +
                        "[L]${endShiftReportResponse.qr}[R]$QR_TICKETS\n" +
                        "[L]${endShiftReportResponse.card}[R]$CARD_TICKETS\n" +
                        "[L]${endShiftReportResponse.totalTickets}[R]$TOTAL_TICKETS\n" +
                        "[L]${endShiftReportResponse.totalIncome}[R]$TOTAL_INCOME\n".let { String(it.toByteArray(), Charset.forName("UTF-8")) }
            )
    }

    fun print(logo: Bitmap?, pageBitmap: Bitmap) {
        val escPosPrinter =
            EscPosPrinter(
                BluetoothPrintersConnections.selectFirstPaired(),
                203,
                58f,
                32
            )

        val logoHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            logo
        )

        val pageHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            pageBitmap
        )

        escPosPrinter.printFormattedTextAndCut(
            "[C]<img>$logoHex</img>\n" +
                    "[C]=================\n" +
                    "[C]<img>$pageHex</img>\n".let { String(it.toByteArray(), Charset.forName("UTF-8")) }
        )
    }
}
