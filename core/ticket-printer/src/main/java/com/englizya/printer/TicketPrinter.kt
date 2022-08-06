package com.englizya.printer

import android.graphics.Bitmap
import com.englizya.model.PrintableTicket
import com.englizya.model.request.Ticket
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ShiftReportResponse
import com.englizya.model.response.UserTicket


interface TicketPrinter {

    fun printShiftReport(endShiftReportResponse: ShiftReportResponse)
    fun printShiftReport(endShiftReportResponse: LongManifestoEndShiftResponse)
    fun printTicket(ticket: Ticket)
    fun getLogoBitmap(): Bitmap
    fun toQr(value: String): Bitmap
    fun printTickets(tickets: ArrayList<Ticket>)
    fun printTicket(ticket: UserTicket)
    fun printTicket(ticket: PrintableTicket)
    fun printTickets(tickets: List<UserTicket>)
    fun printCashTickets(tickets: List<PrintableTicket>)
}