package com.englizya.api.impl

import com.englizya.api.RemoteTicketService
import com.englizya.api.utils.AuthenticationParameters
import com.englizya.api.utils.Header
import com.englizya.api.utils.Parameters
import com.englizya.api.utils.Routing
import com.englizya.datastore.core.DriverDataStore
import com.englizya.model.request.Ticket
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*
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

    override suspend fun requestTickets(
        token: String,
        uid: String,
        quantity: Int,
        category: Int
    ) : List<Ticket> {
        return client.post {
            url(Routing.REQUEST_TICKETS_WITH_WALLET)
            parameter(Parameters.UID, uid)
            parameter(Parameters.QUANTITY, quantity)
            parameter(Parameters.CATEGORY, category)
            header(Header.DRIVER_TOKEN,"${AuthScheme.Bearer} $token")
        }
    }

}