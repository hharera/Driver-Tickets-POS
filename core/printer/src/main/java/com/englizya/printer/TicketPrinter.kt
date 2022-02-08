package com.englizya.printer

import android.app.Application
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.utils.ArabicParameters.CARD_TICKETS
import com.englizya.printer.utils.ArabicParameters.CAR_CODE
import com.englizya.printer.utils.ArabicParameters.CASH_TICKETS
import com.englizya.printer.utils.ArabicParameters.DRIVER_CODE
import com.englizya.printer.utils.ArabicParameters.GRAY_LEVEL
import com.englizya.printer.utils.ArabicParameters.INVERT_STATE
import com.englizya.printer.utils.ArabicParameters.LEFT_INDENT
import com.englizya.printer.utils.ArabicParameters.LINE_CODE
import com.englizya.printer.utils.ArabicParameters.MANIFESTO_DATE
import com.englizya.printer.utils.ArabicParameters.QR_TICKETS
import com.englizya.printer.utils.ArabicParameters.SERIAL
import com.englizya.printer.utils.ArabicParameters.SHIFT_END
import com.englizya.printer.utils.ArabicParameters.SHIFT_START
import com.englizya.printer.utils.ArabicParameters.SPACE_SET
import com.englizya.printer.utils.ArabicParameters.TICKET_CATEGORY
import com.englizya.printer.utils.ArabicParameters.TICKET_DATE
import com.englizya.printer.utils.ArabicParameters.TICKET_TIME
import com.englizya.printer.utils.ArabicParameters.TOTAL_INCOME
import com.englizya.printer.utils.ArabicParameters.TOTAL_TICKETS
import com.englizya.printer.utils.TicketParameter.TEXT_SIZE
import com.englizya.printer.utils.TicketParameter.TEXT_STYLE
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.gl.page.IPage
import com.pax.gl.page.IPage.EAlign
import com.pax.gl.page.PaxGLPage
import java.util.*
import javax.inject.Inject


class TicketPrinter @Inject constructor(
    private val paxPrinter: PaxPrinter,
    private val paxGLPage: PaxGLPage,
    private val application: Application,
) {

    init {
        paxPrinter.apply { init() }
        paxPrinter.fontSet(EFontTypeAscii.FONT_16_16, EFontTypeExtCode.FONT_16_16)
        paxPrinter.spaceSet(SPACE_SET, SPACE_SET)
        paxPrinter.leftIndents(LEFT_INDENT)
        paxPrinter.setGray(GRAY_LEVEL)
        paxPrinter.setInvert(INVERT_STATE)
        paxPrinter.setDoubleWidth(isAscDouble = true, isLocalDouble = true)
        paxPrinter.setDoubleHeight(isAscDouble = true, isLocalDouble = true)
    }

    fun printShiftReport(endShiftReportResponse: ShiftReportResponse) {
        val page = paxGLPage.createPage()
        page.adjustLineSpace(-9)

        val logo =
            BitmapFactory.decodeResource(application.baseContext.resources, R.drawable.ic_ticket_logo)
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
            "$MANIFESTO_DATE${endShiftReportResponse.date}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$SHIFT_START${endShiftReportResponse.startTime}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit(
            "$SHIFT_END${endShiftReportResponse.endTime}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

//        page.addLine().addUnit("$WORK_HOURS${TimeUtils.calculateWorkHours(endShiftReportResponse.startTime, endShiftReportResponse.endTime)}", , TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
//        paxPrinter.step(12)

        page.addLine().addUnit(
            "$CASH_TICKETS${endShiftReportResponse.cash}",
            TEXT_SIZE,
            EAlign.CENTER,
            TEXT_STYLE
        )
        page.addLine().addUnit("\n", 5)

        page.addLine()
            .addUnit("$QR_TICKETS${endShiftReportResponse.qr}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
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

        paxPrinter.printBitmap(pageBitmap)
        paxPrinter.start()
    }

    private fun printTicket(ticket: Ticket) {
        val page = paxGLPage.createPage()
        page.adjustLineSpace(-9)


        val logo = getLogoBitmap()
        page.addLine().addUnit(logo, EAlign.CENTER)
        page.addLine().addUnit("\n", 12)

        page.addLine()
            .addUnit(
                "$SERIAL${ticket.carCode}_${TimeUtils.getTicketTimeMillis()}",
                TEXT_SIZE,
                EAlign.CENTER,
                TEXT_STYLE
            )
        page.addLine().addUnit("\n", 5)

        page.addLine()
            .addUnit("$DRIVER_CODE${ticket.driverCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit("$CAR_CODE${ticket.carCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)


        page.addLine().addUnit("$LINE_CODE${ticket.lineCode}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit("$TICKET_DATE${TimeUtils.getDate(ticket.date)}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 5)

        page.addLine().addUnit("$TICKET_TIME${TimeUtils.getTime(ticket.date)}", TEXT_SIZE, EAlign.CENTER, TEXT_STYLE)
        page.addLine().addUnit("\n", 10)

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

        page.addLine().addUnit("\n", 35)

        val ticketBitmap = paxGLPage.pageToBitmap(page, 384)

        paxPrinter.printBitmap(ticketBitmap)
        paxPrinter.start()
    }

    private fun getLogoBitmap() =
        BitmapFactory.decodeResource(
            application.applicationContext.resources,
            R.drawable.ic_ticket_logo
        )

    fun printTickets(tickets: ArrayList<Ticket>) {
        tickets.forEach { ticket ->
            printTicket(ticket = ticket)
        }
    }
}