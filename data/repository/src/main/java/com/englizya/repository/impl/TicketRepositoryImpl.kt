package com.englizya.repository.impl

import com.englizya.api.RemoteTicketService
import com.englizya.local.TicketDao
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
}