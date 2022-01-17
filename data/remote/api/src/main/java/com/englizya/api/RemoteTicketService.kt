package com.englizya.api

import com.englizya.model.request.Ticket

interface RemoteTicketService {
    suspend fun insertTicket(ticket : Ticket): Ticket
    suspend fun insertTickets(tickets : List<Ticket>): List<Ticket>
}