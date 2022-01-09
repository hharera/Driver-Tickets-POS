package com.englizya.api.impl

import com.englizya.api.RemoteTicketService
import com.englizya.api.utils.Routing
import com.englizya.model.request.Ticket
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteTicketServiceImpl @Inject constructor(
    private val client: HttpClient,
) : RemoteTicketService {

    override suspend fun insertTicket(ticket: Ticket): String {
        return client.post {
            url(Routing.TICKET)
            contentType(ContentType.Application.Json)
            body = ticket
        }
    }

    override suspend fun insertTickets(tickets: List<Ticket>): List<String> {
        return client.post {
            url(Routing.TICKETS)
            contentType(ContentType.Application.Json)
            body = tickets
        }
    }

}