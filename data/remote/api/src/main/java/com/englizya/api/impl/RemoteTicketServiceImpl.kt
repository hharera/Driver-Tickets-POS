package com.englizya.api.impl

import com.englizya.api.RemoteTicketService
import com.englizya.api.utils.AuthenticationParameters
import com.englizya.api.utils.Routing
import com.englizya.datastore.core.DriverDataStore
import com.englizya.model.request.Ticket
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteTicketServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val driverDataStore: DriverDataStore,
) : RemoteTicketService {

    override suspend fun insertTicket(ticket: Ticket): Ticket {
        return client.post {
            url(Routing.TICKET)
            contentType(ContentType.Application.Json)
            body = ticket
            headers.append(
                name = HttpHeaders.Authorization,
                value = "${AuthenticationParameters.BEARER} ${driverDataStore.getToken()}"
            )
        }
    }

    override suspend fun insertTickets(tickets: List<Ticket>): List<Ticket> {
        return client.post {
            url(Routing.TICKETS)
            contentType(ContentType.Application.Json)
            body = tickets
            headers.append(
                name = HttpHeaders.Authorization,
                value = "${AuthenticationParameters.BEARER} ${driverDataStore.getToken()}"
            )
        }
    }

}