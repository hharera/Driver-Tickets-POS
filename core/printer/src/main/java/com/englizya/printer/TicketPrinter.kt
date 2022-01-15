package com.englizya.printer

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.englizya.model.request.Ticket
import com.englizya.printer.utils.Constants.CAR_CODE
import com.englizya.printer.utils.Constants.CUSTOMER_SUPPORT
import com.englizya.printer.utils.Constants.DRIVER_CODE
import com.englizya.printer.utils.Constants.GRAY_LEVEL
import com.englizya.printer.utils.Constants.INVERT_STATE
import com.englizya.printer.utils.Constants.LEFT_INDENT
import com.englizya.printer.utils.Constants.SERIAL
import com.englizya.printer.utils.Constants.SPACE_SET
import com.englizya.printer.utils.Constants.TICKET_TIME
import com.englizya.printer.utils.Time
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

    private fun printTicket(ticket: Ticket) {
        val options = BitmapFactory.Options().apply { inScaled = true }
        val logo =
            BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_ticket_logo, options)
        paxPrinter?.printBitmap(logo)
        paxPrinter?.step(120)
        paxPrinter?.printStr("$SERIAL${ticket.carCode}_${Time.getTicketTimeMillis()}", null)
        paxPrinter?.step(30)
        paxPrinter?.printStr("$DRIVER_CODE${ticket.driverCode}", null)
        paxPrinter?.step(30)
        paxPrinter?.printStr("$CAR_CODE${ticket.carCode}", null)
        paxPrinter?.step(30)
//        TODO set time in server from est time zone
        paxPrinter?.printStr("$TICKET_TIME${ticket.time}", null)
        paxPrinter?.step(30)

        paxPrinter?.printStr("\n\nCOST 5\n\n", null)
        paxPrinter?.printStr("\nCustomer support tele : $CUSTOMER_SUPPORT\n", null)

//        logo = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.cat_5, options)
//        paxPrinter.printBitmap(logo)
//        paxPrinter.step(80)

        paxPrinter?.start()
    }

    fun printTickets(tickets: ArrayList<Ticket>) {
        tickets.forEach { ticket ->
            printTicket(ticket = ticket)
        }
    }
}