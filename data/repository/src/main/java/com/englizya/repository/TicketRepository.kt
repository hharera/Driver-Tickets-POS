package com.englizya.repository

import com.englizya.model.request.Ticket


interface TicketRepository {

    suspend fun insertTicket(ticket : Ticket, forceOnline : Boolean) : Result<Unit>
    suspend fun insertTickets(tickets : List<Ticket>, forceOnline : Boolean) : Result<Unit>
    suspend fun getLocalTickets(): Result<List<Ticket>>
    suspend fun deleteLocalTickets(): Result<Unit>
}