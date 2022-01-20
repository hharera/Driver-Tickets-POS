package com.englizya.printer

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.englizya.model.request.Ticket
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.utils.Constants.CARD_TICKETS
import com.englizya.printer.utils.Constants.CAR_CODE
import com.englizya.printer.utils.Constants.CASH_TICKETS
import com.englizya.printer.utils.Constants.DRIVER_CODE
import com.englizya.printer.utils.Constants.GRAY_LEVEL
import com.englizya.printer.utils.Constants.INVERT_STATE
import com.englizya.printer.utils.Constants.LEFT_INDENT
import com.englizya.printer.utils.Constants.LINE_CODE
import com.englizya.printer.utils.Constants.MANIFESTO_DATE
import com.englizya.printer.utils.Constants.QR_TICKETS
import com.englizya.printer.utils.Constants.SERIAL
import com.englizya.printer.utils.Constants.SHIFT_END
import com.englizya.printer.utils.Constants.SHIFT_START
import com.englizya.printer.utils.Constants.SPACE_SET
import com.englizya.printer.utils.Constants.TICKET_CATEGORY
import com.englizya.printer.utils.Constants.TICKET_TIME
import com.englizya.printer.utils.Constants.TOTAL_INCOME
import com.englizya.printer.utils.Constants.TOTAL_TICKETS
import com.englizya.printer.utils.Constants.WORK_HOURS
import com.englizya.common.utils.time.TimeUtils
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import java.util.*
import javax.inject.Inject


class TicketPrinter @Inject constructor(
    private val paxPrinter: PaxPrinter?
) {

    init {
        paxPrinter?.apply { init() }
        paxPrinter?.fontSet(EFontTypeAscii.FONT_16_16, EFontTypeExtCode.FONT_16_16)
        paxPrinter?.spaceSet(SPACE_SET, SPACE_SET)
        paxPrinter?.leftIndents(LEFT_INDENT)
        paxPrinter?.setGray(GRAY_LEVEL)
        paxPrinter?.setInvert(INVERT_STATE)
        paxPrinter?.setDoubleWidth(isAscDouble = true, isLocalDouble = true)
        paxPrinter?.setDoubleHeight(isAscDouble = true, isLocalDouble = true)
    }

    fun printShiftReport(endShiftReportResponse: ShiftReportResponse) {
        val options = BitmapFactory.Options().apply { inScaled = true }
        val logo =
            BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_ticket_logo, options)
        paxPrinter?.printBitmap(logo)
        paxPrinter?.step(120)

        paxPrinter?.printStr("$DRIVER_CODE${endShiftReportResponse.driverCode}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$CAR_CODE${endShiftReportResponse.carCode}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$LINE_CODE${endShiftReportResponse.lineCode}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$MANIFESTO_DATE${endShiftReportResponse.date}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$SHIFT_START${endShiftReportResponse.startTime}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$SHIFT_END${endShiftReportResponse.endTime}", null)
        paxPrinter?.step(30)

//        paxPrinter?.printStr("$WORK_HOURS${TimeUtils.calculateWorkHours(endShiftReportResponse.startTime, endShiftReportResponse.endTime)}", null)
//        paxPrinter?.step(30)

        paxPrinter?.printStr("$CASH_TICKETS${endShiftReportResponse.cash}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$QR_TICKETS${endShiftReportResponse.qr}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$CARD_TICKETS${endShiftReportResponse.card}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$TOTAL_TICKETS${endShiftReportResponse.totalTickets}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$TICKET_CATEGORY${endShiftReportResponse.ticketCategory}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$TOTAL_INCOME${endShiftReportResponse.totalIncome}", null)
        paxPrinter?.step(30)

        paxPrinter?.start()
    }

    private fun printTicket(ticket: Ticket) {
        val options = BitmapFactory.Options().apply { inScaled = true }
        val logo =
            BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_ticket_logo, options)

        paxPrinter?.printBitmap(logo)
        paxPrinter?.step(120)

        paxPrinter?.printStr("$SERIAL${ticket.carCode}_${TimeUtils.getTicketTimeMillis()}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$DRIVER_CODE${ticket.driverCode}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$CAR_CODE${ticket.carCode}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("$TICKET_TIME${ticket.time}", null)
        paxPrinter?.step(30)

        val ticketCategoryBitmap =
            BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.cat_5, options)
        paxPrinter?.printBitmap(ticketCategoryBitmap)
        paxPrinter?.step(50)

        paxPrinter?.start()
    }

    fun printTickets(tickets: ArrayList<Ticket>) {
        tickets.forEach { ticket ->
            printTicket(ticket = ticket)
        }
    }
}