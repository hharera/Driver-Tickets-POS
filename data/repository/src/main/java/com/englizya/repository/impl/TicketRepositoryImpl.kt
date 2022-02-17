package com.englizya.repository.impl

import com.englizya.api.RemoteTicketService
import com.englizya.local.TicketDao
import com.englizya.model.UnPrintedTicket
import com.englizya.model.request.Ticket
import com.englizya.repository.TicketRepository
import javax.inject.Inject


class TicketRepositoryImpl @Inject constructor(
    private val ticketService: RemoteTicketService,
    private val ticketDao: TicketDao,
) : TicketRepository {

    override suspend fun insertTicket(ticket: Ticket, forceOnline: Boolean): Result<Unit> =
        kotlin.runCatching {
            if (forceOnline) {
                ticketService.insertTicket(ticket)
            } else {
                ticketDao.insertTicket(ticket)
            }
        }

    override suspend fun insertUnPrintedTicket(ticket: Ticket): Result<Unit> = kotlin.runCatching {
        ticketDao.insertUnPrintedTicket(
            UnPrintedTicket(
                ticketCategory = ticket.ticketCategory,
                carCode = ticket.carCode,
                driverCode = ticket.driverCode,
                lineCode = ticket.lineCode,
                manifestoId = ticket.manifestoId,
                manifestoYear = ticket.manifestoYear,
                paymentWay = ticket.paymentWay,
                ticketId = ticket.ticketId,
                ticketLatitude = ticket.ticketLatitude,
                ticketLongitude = ticket.ticketLongitude,
                time = ticket.time
            )
        )
    }

    override suspend fun insertTickets(tickets: List<Ticket>, forceOnline: Boolean): Result<Unit> =
        kotlin.runCatching {
            if (forceOnline) {
                ticketService.insertTickets(tickets)
            } else {
                ticketDao.insertTickets(tickets)
            }
        }

    override suspend fun getLocalTickets(): Result<List<Ticket>> = kotlin.runCatching {
        ticketDao.getTickets()
    }

    override suspend fun deleteLocalTickets(): Result<Unit> = kotlin.runCatching {
        ticketDao.deleteAll()
    }

    override fun getAllUnPrintedTickets(): Result<List<UnPrintedTicket>> = kotlin.runCatching {
        ticketDao.getAllSavedTickets()
    }
}