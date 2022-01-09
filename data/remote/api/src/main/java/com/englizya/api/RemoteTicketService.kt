package com.englizya.api

import com.englizya.model.request.Ticket

interface RemoteTicketService {
    suspend fun insertTicket(ticket : Ticket): String
    suspend fun insertTickets(tickets : List<Ticket>): List<String>
}