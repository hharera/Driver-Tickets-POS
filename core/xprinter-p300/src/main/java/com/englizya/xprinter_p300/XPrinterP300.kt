package com.englizya.xprinter_p300

import android.graphics.Bitmap
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.xprinter_p300.Parameters.CARD_TICKETS
import com.englizya.xprinter_p300.Parameters.CAR_CODE
import com.englizya.xprinter_p300.Parameters.CASH_TICKETS
import com.englizya.xprinter_p300.Parameters.DRIVER_CODE
import com.englizya.xprinter_p300.Parameters.LINE_CODE
import com.englizya.xprinter_p300.Parameters.MANIFESTO_DATE
import com.englizya.xprinter_p300.Parameters.QR_TICKETS
import com.englizya.xprinter_p300.Parameters.SHIFT_END
import com.englizya.xprinter_p300.Parameters.SHIFT_START
import com.englizya.xprinter_p300.Parameters.TOTAL_INCOME
import com.englizya.xprinter_p300.Parameters.TOTAL_TICKETS
import com.englizya.xprinter_p300.Parameters.WORK_HOURS
import java.nio.charset.Charset


object XPrinterP300 {

    fun print(ticket: Ticket, logo: Bitmap) {
        val printer =
            EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
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
        escPosPrinter: EscPosPrinter,
        logo: Bitmap,
        tele: Bitmap,
        page: Bitmap,
    ) {
        val logoHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            logo
        )

        val teleHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            tele
        )

        val pageHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            page
        )

        escPosPrinter.printFormattedText(
            "\n[C]<img>$logoHex</img>\n" +
                    "[C]<img>$pageHex</img>\n" +
                    "[C]<img>$teleHex</img>\n"
        )
    }

    fun print(
        escPosPrinter: EscPosPrinter,
        logo: Bitmap,
        endShiftReportResponse: ShiftReportResponse
    ) {
        val logoHex = PrinterTextParserImg.bitmapToHexadecimalString(
            escPosPrinter,
            logo
        )

        escPosPrinter
            .printFormattedTextAndCut(
                "[C]<img>$logoHex</img>\n" +
                        "[L]$DRIVER_CODE[R]${endShiftReportResponse.driverCode}\n" +
                        "[L]$CAR_CODE[R]${endShiftReportResponse.carCode}\n" +
                        "[L]$LINE_CODE[R]${endShiftReportResponse.lineCode}\n" +
                        "[L]$MANIFESTO_DATE[R]${endShiftReportResponse.date}\n" +
                        "[L]$SHIFT_START ${endShiftReportResponse.startTime}\n" +
                        "[L]$SHIFT_END ${endShiftReportResponse.endTime}\n" +
                        "[L]$WORK_HOURS[R]${TimeUtils.calculateWorkHours(endShiftReportResponse)}\n" +
                        "[L]$CASH_TICKETS[R]${endShiftReportResponse.cash}\n" +
                        "[L]$QR_TICKETS[R]${endShiftReportResponse.qr}\n" +
                        "[L]$CARD_TICKETS[R]${endShiftReportResponse.card}\n" +
                        "[L]$TOTAL_TICKETS[R]${endShiftReportResponse.totalTickets}\n" +
                        "[L]$TOTAL_INCOME[R]${endShiftReportResponse.totalIncome}\n"
            )
    }

    fun print(
        escpos : EscPosPrinter,
        bitmap: Bitmap
    ) {
        PrinterTextParserImg.bitmapToHexadecimalString(
            escpos,
            bitmap
        ).let {
            escpos
                .printFormattedTextAndCut(
                    "[C]<img>$it</img>\n"
                )
        }
    }
}

