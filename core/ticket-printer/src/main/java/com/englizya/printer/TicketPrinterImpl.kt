package com.englizya.printer

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.englizya.common.date.DateOnly
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.PrintableTicket
import com.englizya.model.request.Ticket
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ShiftReportResponse
import com.englizya.model.response.UserTicket
import com.englizya.printer.utils.ArabicParameters
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
import com.englizya.printer.utils.ArabicParameters.TICKET_DATE
import com.englizya.printer.utils.ArabicParameters.TICKET_DETAILS
import com.englizya.printer.utils.ArabicParameters.TICKET_TIME
import com.englizya.printer.utils.ArabicParameters.TOTAL_INCOME
import com.englizya.printer.utils.ArabicParameters.TOTAL_TICKETS
import com.englizya.printer.utils.ArabicParameters.WORK_HOURS
import com.englizya.printer.utils.TicketParameter.TEXT_SIZE
import com.englizya.printer.utils.TicketParameter.TEXT_STYLE
import com.englizya.sunmiprinter.SunmiPrinter
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.pax.gl.page.IPage.EAlign
import com.pax.gl.page.PaxGLPage


class TicketPrinterImpl constructor(
    private val paxGLPage: PaxGLPage,
    private val application: Application,
    private val sunmiPrinter: SunmiPrinter,
) : TicketPrinter {
    private val TAG = "TicketPrinter"

    override fun printShiftReport(endShiftReportResponse: ShiftReportResponse) {
        val page = paxGLPage.createPage()
        page.adjustLineSpace(-9)

        val logo =
            BitmapFactory.decodeResource(
                application.baseContext.resources,
                R.drawable.ic_ticket_logo
            )
        page.addLine().addUnit(logo, EAlign.CENTER)
        page.addLine().addUnit("\n", 7)

        page.addLine().addUnit(
            "$DRIVER_CODE${endShiftReportResponse.driverCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$CAR_CODE${endShiftReportResponse.carCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$LINE_CODE${endShiftReportResponse.lineCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "${MANIFESTO_DATE.plus("\n")}${endShiftReportResponse.date}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            SHIFT_START,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            endShiftReportResponse.startTime,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            SHIFT_END,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            endShiftReportResponse.endTime,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "${WORK_HOURS.plus("\n")}${TimeUtils.calculateWorkHours(endShiftReportResponse)}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$CASH_TICKETS${endShiftReportResponse.cash}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine()
            .addUnit(
                "$QR_TICKETS${endShiftReportResponse.qr}",
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$CARD_TICKETS${endShiftReportResponse.card}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$TOTAL_TICKETS${endShiftReportResponse.totalTickets}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$TICKET_CATEGORY${endShiftReportResponse.ticketCategory}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$TOTAL_INCOME${endShiftReportResponse.totalIncome}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )

        page.addLine().addUnit("\n", 35)
        val pageBitmap = paxGLPage.pageToBitmap(page, 384)

        sunmiPrinter.print(pageBitmap)
    }

    override fun printShiftReport(endShiftReportResponse: LongManifestoEndShiftResponse) {
        val page = paxGLPage.createPage()
        page.adjustLineSpace(-9)

        val logo =
            BitmapFactory.decodeResource(
                application.baseContext.resources,
                R.drawable.ic_ticket_logo
            )
        page.addLine().addUnit(logo, EAlign.CENTER)
        page.addLine().addUnit("\n", 7)

        page.addLine().addUnit(
            "$DRIVER_CODE${endShiftReportResponse.driverCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$CAR_CODE${endShiftReportResponse.carCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$LINE_CODE${endShiftReportResponse.lineCode}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "${MANIFESTO_DATE.plus("\n")}${TimeUtils.getDate(endShiftReportResponse.manifestoDate)}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            SHIFT_START,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "${TimeUtils.getDate(endShiftReportResponse.shiftStartTime)}  ${
                TimeUtils.getTime(
                    endShiftReportResponse.shiftStartTime
                )
            }",

            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            SHIFT_END,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "${TimeUtils.getDate(endShiftReportResponse.shiftEndTime)}  ${
                TimeUtils.getTime(
                    endShiftReportResponse.shiftEndTime
                )
            }",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "${WORK_HOURS.plus("\n")}${TimeUtils.calculateWorkHoursAndMinutes(endShiftReportResponse)}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)
        page.addLine().addUnit(
            TICKET_DETAILS,
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        endShiftReportResponse.ticketDetails.forEach { (key, value) ->
            page.addLine().addUnit(
                "$value X $key",
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
            page.addLine().addUnit("\n", 5)
        }



        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$TOTAL_TICKETS${endShiftReportResponse.totalTickets}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)



        page.addLine().addUnit(
            "$TOTAL_INCOME${endShiftReportResponse.totalIncome}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )

        page.addLine().addUnit("\n", 35)
        val pageBitmap = paxGLPage.pageToBitmap(page, 384)

        sunmiPrinter.print(pageBitmap)
    }

    override fun printTicket(ticket: Ticket) {
        val page = paxGLPage.createPage()
        page.adjustLineSpace(-9)


        val logo = getLogoBitmap()
        page.addLine().addUnit(logo, EAlign.CENTER)
        page.addLine().addUnit("\n", 12)

        page.addLine()
            .addUnit(
                ticket.ticketId,
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(toQr(ticket.ticketId), EAlign.CENTER)
        page.addLine().addUnit("\n", 5)

        page.addLine()
            .addUnit("$DRIVER_CODE${ticket.driverCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit("$CAR_CODE${ticket.carCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)


        page.addLine().addUnit("$LINE_CODE${ticket.lineCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$TICKET_DATE${TimeUtils.getDate(ticket.time)}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$TICKET_TIME${TimeUtils.getTime(ticket.time)}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$TICKET_CATEGORY${ticket.ticketCategory}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        val teleBitmap =
            BitmapFactory.decodeResource(
                application.applicationContext.resources,
                R.drawable.tele
            )
        page.addLine().addUnit(teleBitmap, EAlign.CENTER)

        page.addLine().addUnit("\n", 35)

        val ticketBitmap = paxGLPage.pageToBitmap(page, 384)

        sunmiPrinter.print(ticketBitmap)
    }

    override fun getLogoBitmap() =
        BitmapFactory.decodeResource(
            application.applicationContext.resources,
            R.drawable.ic_ticket_logo
        )

    override fun printTickets(tickets: ArrayList<Ticket>) {
        return tickets.forEach { ticket ->
            printTicket(ticket = ticket)
        }
    }

    override fun printTicket(ticket: UserTicket) {
        val page = paxGLPage.createPage()

        val logo = getLogoBitmap()

        page.addLine().addUnit(logo, EAlign.CENTER)

        page.addLine().addUnit(ticket.ticketId.toString(), TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)

        page.addLine().addUnit(toQr(ticket.ticketQr), EAlign.CENTER)

        page.addLine().addUnit(
            "${ArabicParameters.SOURCE}${ticket.source}".plus("\n")
                .plus("${ArabicParameters.DESTINATION}${ticket.destination}")
                .plus("\n")
                .plus("${ArabicParameters.RESERVATION_DATE}${DateOnly.toMonthDate(ticket.reservationDate)}")
                .plus("\n").plus("${ArabicParameters.SERVICE_DEGREE}${ticket.serviceType}")
                .plus("\n")
                .plus("${ArabicParameters.TRIP}${ticket.tripName}").plus("\n")
                .plus("${ArabicParameters.SEAT_NO}${ticket.seatNo}"),
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )

        val tele =
            BitmapFactory.decodeResource(application.applicationContext.resources, R.drawable.tele)

        page.addLine().addUnit(tele, EAlign.CENTER)
        page.addLine().addUnit("\n", 28)

        sunmiPrinter.print(page.toBitmap(384))
    }

    override fun printTicket(ticket: PrintableTicket) {
        val page = paxGLPage.createPage()

        val logo = getLogoBitmap()

        page.addLine().addUnit(logo, EAlign.CENTER)

        page.addLine().addUnit(ticket.ticketId.toString(), TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)

        page.addLine().addUnit(toQr(ticket.ticketQr), EAlign.CENTER)

        page.addLine().addUnit(
            "${ArabicParameters.SOURCE}${ticket.source}".plus("\n")
                .plus("${ArabicParameters.DESTINATION}${ticket.destination}")
                .plus("\n")
                .plus("${ArabicParameters.PRINTING_TIME}${DateOnly.toMonthDate(ticket.ticketingTime)}")
                .plus("\n").plus("${ArabicParameters.SERVICE_DEGREE}${ticket.serviceDegree}")
                .plus("\n")
                .plus("${ArabicParameters.TRIP}${ticket.tripName}").plus("\n")
                .plus("${ArabicParameters.SEAT_NO}${ticket.seatNo}")
                .plus("\n")
                .plus("${ArabicParameters.TICKET_PRICE}${ticket.ticketPrice}"),
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )

        val tele =
            BitmapFactory.decodeResource(application.applicationContext.resources, R.drawable.tele)

        page.addLine().addUnit(tele, EAlign.CENTER)
        page.addLine().addUnit("\n", 28)

        sunmiPrinter.print(page.toBitmap(384))
    }

     override fun toQr(ticketId: String): Bitmap {
        return BarcodeEncoder().encodeBitmap(ticketId, BarcodeFormat.QR_CODE, 150, 150)
    }

    override fun printTickets(tickets: List<UserTicket>) {
        tickets.forEach { ticket ->
            printTicket(ticket)
        }
    }

    override fun printCashTickets(tickets: List<PrintableTicket>) {
        tickets.forEach { ticket ->
            printTicket(ticket)
        }
    }
}