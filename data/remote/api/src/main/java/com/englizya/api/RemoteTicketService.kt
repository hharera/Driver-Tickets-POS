package com.englizya.api

import com.englizya.model.request.Ticket

interface RemoteTicketService {
    suspend fun insertTicket(ticket : Ticket): Ticket
    suspend fun insertTickets(tickets: List<Ticket>): List<Ticket>
    suspend fun requestTickets(token: String, uid: String, quantity: Int, category: Int) : List<Ticket>
}