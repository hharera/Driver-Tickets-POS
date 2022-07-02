package com.englizya.printer

import android.app.Application
import android.graphics.*
import com.dantsu.escposprinter.EscPosPrinter
import com.englizya.common.date.DateOnly
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.model.response.TourismTicket
import com.englizya.model.response.UserTicket
import com.englizya.printer.utils.ArabicParameters
import com.englizya.printer.utils.ArabicParameters.CARD_TICKETS
import com.englizya.printer.utils.ArabicParameters.CAR_CODE
import com.englizya.printer.utils.ArabicParameters.CASH_TICKETS
import com.englizya.printer.utils.ArabicParameters.DESTINATION
import com.englizya.printer.utils.ArabicParameters.DRIVER_CODE
import com.englizya.printer.utils.ArabicParameters.LINE_CODE
import com.englizya.printer.utils.ArabicParameters.MANIFESTO_DATE
import com.englizya.printer.utils.ArabicParameters.QR_TICKETS
import com.englizya.printer.utils.ArabicParameters.SEAT_NO
import com.englizya.printer.utils.ArabicParameters.SERVICE_DEGREE
import com.englizya.printer.utils.ArabicParameters.SHIFT_END
import com.englizya.printer.utils.ArabicParameters.SHIFT_START
import com.englizya.printer.utils.ArabicParameters.SOURCE
import com.englizya.printer.utils.ArabicParameters.TICKET_CATEGORY
import com.englizya.printer.utils.ArabicParameters.TICKET_DATE
import com.englizya.printer.utils.ArabicParameters.TICKET_TIME
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


class TicketPrinter constructor(
    private val paxGLPage: PaxGLPage,
    private val escPosPrinter: EscPosPrinter,
    private val application: Application,
) {
    private val TAG = "TicketPrinter"


    fun printShiftReport(endShiftReportResponse: ShiftReportResponse): String {
        val page = paxGLPage.createPage()
        page.addLine().addUnit("", 1)

        val logo = getLogoBitmap()
        page.addLine().addUnit("\n", 28)

        page.addLine().addUnit(
            "$DRIVER_CODE${endShiftReportResponse.driverCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$CAR_CODE${endShiftReportResponse.carCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$LINE_CODE${endShiftReportResponse.lineCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$MANIFESTO_DATE${endShiftReportResponse.date}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            SHIFT_START, TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            endShiftReportResponse.startTime, TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            SHIFT_END, TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            endShiftReportResponse.endTime, TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
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
            "$CASH_TICKETS${endShiftReportResponse.cash}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
        )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
                "$QR_TICKETS${endShiftReportResponse.qr}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
            )
        page.addLine().addUnit("\n", 10)

        page.addLine().addUnit(
            "$CARD_TICKETS${endShiftReportResponse.card}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
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
        XPrinterP300.print(escPosPrinter, logo, endShiftReportResponse)
        return "OK"
    }

    fun printTourismTicket(ticket: TourismTicket): String {
        val logo = getLogoBitmap()
        val category = BitmapFactory.decodeResource(
            application.applicationContext.resources, R.drawable.cat_5
        )
        val tele = BitmapFactory.decodeResource(
            application.applicationContext.resources, R.drawable.tele
        )

        val page = paxGLPage.createPage()

        page.addLine().addUnit(
                ticket.ticketId, TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
            )

        page.addLine().addUnit(
                getTicketQr(ticket.ticketId),
                EAlign.CENTER,
            )

        page.addLine().addUnit(
                String().plus("$SEAT_NO${ticket.seatNo}").plus("\n")
                    .plus("${ArabicParameters.TRIP_ID}${ticket.tripId}").plus("\n")
                    .plus("$SOURCE${ticket.source}").plus("\n")
                    .plus("$DESTINATION${ticket.destination}").plus("\n")
                    .plus("$SERVICE_DEGREE${ticket.serviceDegree}").plus("\n")
                    .plus("$TICKET_CATEGORY${ticket.ticketCategory}").plus("\n")
                    .plus("$TICKET_DATE${TimeUtils.getDate(ticket.time)}").plus("\n")
                    .plus("$TICKET_TIME${TimeUtils.getTime(ticket.time)}"),
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )


        XPrinterP300.print(
            escPosPrinter, logo, tele, page.toBitmap(5000)
        )

        return "OK"
    }

    fun printTicket(ticket: Ticket): String {
        val logo = getLogoBitmap()
        val category = BitmapFactory.decodeResource(
            application.applicationContext.resources, R.drawable.cat_5
        )
        val tele = BitmapFactory.decodeResource(
            application.applicationContext.resources, R.drawable.tele
        )

        val page = paxGLPage.createPage()

        page.addLine().addUnit(
                ticket.ticketId, TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
            )

        page.addLine().addUnit(
                getTicketQr(ticket.ticketId),
                EAlign.CENTER,
            )

        page.addLine().addUnit(
                "$DRIVER_CODE${ticket.driverCode}".plus("\n").plus("$CAR_CODE${ticket.carCode}")
                    .plus("\n").plus("$LINE_CODE${ticket.lineCode}").plus("\n")
                    .plus("$TICKET_CATEGORY${ticket.ticketCategory}").plus("\n")
                    .plus("$TICKET_DATE${TimeUtils.getDate(ticket.time)}").plus("\n")
                    .plus("$TICKET_TIME${TimeUtils.getTime(ticket.time)}"),
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )


        XPrinterP300.print(
            escPosPrinter, logo, tele, page.toBitmap(5000)
        )

        return "OK"
    }

    private fun getTicketQr(ticketId: String): Bitmap {
        return BarcodeEncoder().encodeBitmap(ticketId, BarcodeFormat.QR_CODE, 1500, 1500)
    }

    private fun getLogoBitmap() = BitmapFactory.decodeResource(
        application.applicationContext.resources, R.drawable.ic_ticket_logo
    )

    fun printTickets(tickets: ArrayList<Ticket>): List<String> {
        return tickets.map { ticket ->
            printTicket(ticket = ticket)
        }
    }

    fun printTickets(tickets: List<UserTicket>) {
        tickets.forEach {
            printTicket(it)
        }
    }

    fun printArabicText() {
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.WHITE)
        val canvas = Canvas(bitmap)
        canvas.drawText("السلام عليكم", 0f, 0f, Paint().apply { color = Color.BLACK })
        XPrinterP300.print(escPosPrinter, bitmap)
    }

    fun printBitmap(bitmap: Bitmap) {
        XPrinterP300.print(escPosPrinter, bitmap)
    }

    fun drawTextToBitmap(
        text: String
    ): Bitmap? {
        var bitmap = Bitmap.createBitmap(420, 200, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.WHITE)
        var bitmapConfig = bitmap.config
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true)
        val canvas = Canvas(bitmap)
        // new antialised Paint
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        // text color - #3D3D3D
        paint.color = Color.rgb(61, 61, 61)
        // text size in pixels
        paint.textSize = (14 * 3).toInt().toFloat()
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE)

        // draw text to the Canvas center
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val x = (bitmap.width - bounds.width()) / 2
        val y = (bitmap.height + bounds.height()) / 2
        canvas.drawText(text, x.toFloat(), y.toFloat(), paint)
        return bitmap
    }

    fun printTicket(ticket: UserTicket) {
        val logo = getLogoBitmap()

        val tele = BitmapFactory.decodeResource(
            application.applicationContext.resources, R.drawable.tele
        )

        val page = paxGLPage.createPage()

        page.addLine().addUnit(
                ticket.ticketId.toString(), TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
            )

        page.addLine().addUnit(
                getTicketQr(ticket.ticketQr.toString()),
                EAlign.CENTER,
            )

        page.addLine().addUnit(
                "$SOURCE${ticket.source}".plus("\n").plus("$DESTINATION${ticket.destination}")
                    .plus("\n")
                    .plus("${ArabicParameters.RESERVATION_DATE}${DateOnly.toMonthDate(ticket.reservationDate)}")
                    .plus("\n").plus("$SERVICE_DEGREE${ticket.serviceType}").plus("\n")
                    .plus("${ArabicParameters.TRIP}${ticket.tripName}").plus("\n")
                    .plus("$SEAT_NO${ticket.seatNo}"), TEXT_SIZE, EAlign.CENTER, TEXT_STYLE
            )


        XPrinterP300.print(
            escPosPrinter, logo, tele, page.toBitmap(5000)
        )
    }
}