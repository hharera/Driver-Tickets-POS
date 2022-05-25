package com.englizya.printer

import android.app.Application
import android.graphics.BitmapFactory
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.utils.ArabicParameters.CARD_TICKETS
import com.englizya.printer.utils.ArabicParameters.CAR_CODE
import com.englizya.printer.utils.ArabicParameters.CASH_TICKETS
import com.englizya.printer.utils.ArabicParameters.DRIVER_CODE
import com.englizya.printer.utils.ArabicParameters.LINE_CODE
import com.englizya.printer.utils.ArabicParameters.MANIFESTO_DATE
import com.englizya.printer.utils.ArabicParameters.QR_TICKETS
import com.englizya.printer.utils.ArabicParameters.SHIFT_END
import com.englizya.printer.utils.ArabicParameters.SHIFT_START
import com.englizya.printer.utils.ArabicParameters.TICKET_CATEGORY
import com.englizya.printer.utils.ArabicParameters.TOTAL_INCOME
import com.englizya.printer.utils.ArabicParameters.TOTAL_TICKETS
import com.englizya.printer.utils.ArabicParameters.WORK_HOURS
import com.englizya.printer.utils.TicketParameter.TEXT_SIZE
import com.englizya.printer.utils.TicketParameter.TEXT_STYLE
import com.englizya.xprinter_p300.XPrinterP300
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.pax.gl.page.IPage.EAlign
import com.pax.gl.page.PaxGLPage
import javax.inject.Inject


class TicketPrinter @Inject constructor(
    private val paxGLPage: PaxGLPage,
    private val application: Application,
) {
    private val TAG = "TicketPrinter"


    fun printShiftReport(endShiftReportResponse: ShiftReportResponse): String {
        val page = paxGLPage.createPage()
        page.addLine().addUnit("", 1)

        val logo =
            BitmapFactory.decodeResource(
                application.baseContext.resources,
                R.drawable.ic_ticket_logo
            )
        page.addLine().addUnit(logo, EAlign.CENTER)
        page.addLine().addUnit("\n", 28)

        page.addLine().addUnit(
            "$DRIVER_CODE${endShiftReportResponse.driverCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$CAR_CODE${endShiftReportResponse.carCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$LINE_CODE${endShiftReportResponse.lineCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$MANIFESTO_DATE${endShiftReportResponse.date}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            SHIFT_START,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            endShiftReportResponse.startTime,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            SHIFT_END,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            endShiftReportResponse.endTime,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$WORK_HOURS: ${TimeUtils.calculateWorkHours(endShiftReportResponse)}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$CASH_TICKETS${endShiftReportResponse.cash}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine()
            .addUnit(
                "$QR_TICKETS${endShiftReportResponse.qr}",
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$CARD_TICKETS${endShiftReportResponse.card}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$TOTAL_TICKETS${endShiftReportResponse.totalTickets}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$TICKET_CATEGORY${endShiftReportResponse.ticketCategory}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$TOTAL_INCOME${endShiftReportResponse.totalIncome}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )

        page.addLine().addUnit("\n", 70)
        val pageBitmap = paxGLPage.pageToBitmap(page, 1400)

        XPrinterP300.print(pageBitmap)
        return "OK"
    }

    fun printTicket(ticket: Ticket): String {
        val page = paxGLPage.createPage()


        val logo = getLogoBitmap()
        page.addLine().addUnit(logo, EAlign.CENTER)
        page.addLine().addUnit("\n", 24)

        page.addLine()
            .addUnit(
                ticket.ticketId,
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
        page.addLine().addUnit("\n", 16)

        val barCodeBitmap =
            BarcodeEncoder().encodeBitmap(ticket.ticketId, BarcodeFormat.QR_CODE, 1800, 1800)

        page.addLine()
            .addUnit(
                "$DRIVER_CODE${ticket.driverCode}"
                    .plus("\n")
                    .plus("$CAR_CODE${ticket.carCode}")
                    .plus("\n")
                    .plus("$LINE_CODE${ticket.lineCode}")
                    .plus("\n")
                    .plus(TimeUtils.getDate(ticket.time))
                    .plus("\n")
                    .plus(TimeUtils.getTime(ticket.time)),
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
            .addUnit(barCodeBitmap, EAlign.LEFT)

        page.addLine().addUnit("\n", 28)

        val ticketCategoryBitmap =
            BitmapFactory.decodeResource(
                application.applicationContext.resources,
                R.drawable.cat_5
            )
        page.addLine().addUnit(ticketCategoryBitmap, EAlign.CENTER)

        val teleBitmap =
            BitmapFactory.decodeResource(
                application.applicationContext.resources,
                R.drawable.tele
            )
        page.addLine().addUnit(teleBitmap, EAlign.CENTER)

        page.addLine().addUnit("\n", 96)

        val ticketBitmap = paxGLPage.pageToBitmap(page, 5000)

        XPrinterP300.print(ticketBitmap)
        return "OK"
    }

    private fun getLogoBitmap() =
        BitmapFactory.decodeResource(
            application.applicationContext.resources,
            R.drawable.ic_ticket_logo
        )

    fun printTickets(tickets: ArrayList<Ticket>): List<String> {
        return tickets.map { ticket ->
            printTicket(ticket = ticket)
        }
    }
}