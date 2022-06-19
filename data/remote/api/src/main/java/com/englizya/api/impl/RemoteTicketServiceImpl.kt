package com.englizya.api.impl

import com.englizya.api.RemoteTicketService
import com.englizya.api.utils.*
import com.englizya.api.utils.Parameters
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.request.Ticket
import com.englizya.model.request.TourismTicketsWithWalletRequest
import com.englizya.model.response.UserTicket
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*

class RemoteTicketServiceImpl constructor(
    private val client: HttpClient,
    private val driverDataStore: LocalTicketPreferences,
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
        category: Int,
//        walletOtp: String,
//        latitude: Double?,
//        longitude: Double?,
    ): List<Ticket> {
        return client.post {
            url(Routing.REQUEST_TICKETS_WITH_WALLET)
            parameter(Parameters.UID, uid)
            parameter(Parameters.QUANTITY, quantity)
            parameter(Parameters.CATEGORY, category)
//            parameter(Parameters.WALLET_OTP, walletOtp)
//            parameter(Parameters.LATITUDE, latitude)
//            parameter(Parameters.LONGITUDE, longitude)
            header(Header.DRIVER_TOKEN, "${AuthScheme.Bearer} $token")
        }
    }

    override suspend fun requestTourismTickets(
        token: String,
        uid: String,
        quantity: Int,
        sourceStationId: Int,
        destinationStationId: Int,
        tripId: Int
    ): List<Ticket> {
        return client.post {
            url(Routing.REQUEST_TICKETS_WITH_WALLET)
            parameter(Parameters.UID, uid)
            parameter(Parameters.QUANTITY, quantity)
            parameter(Parameters.SOURCE_STATION_ID, sourceStationId)
            parameter(Parameters.DESTINATION_STATION_ID, destinationStationId)
            parameter(Parameters.TRIP_ID, tripId)
            header(Header.DRIVER_TOKEN, "${AuthScheme.Bearer} $token")
        }
    }

    override suspend fun requestLongTicketsWithWallet(request: TourismTicketsWithWalletRequest): List<UserTicket> {
        return client.post {
            url(Routing.REQUEST_LONG_TICKETS_WITH_WALLET)
            contentType(ContentType.Application.Json)
            body = request
            timeout {
                requestTimeoutMillis = 10000
            }
        }
    }

    override suspend fun requestReservedTicket(token: String, uid: String): UserTicket {
        return client.get {
            url(Routing.GET_RESERVED_TICKET)
            contentType(ContentType.Application.Json)
            parameter(Parameters.TICKET_QR , uid)
            header(Header.DRIVER_TOKEN, "${AuthScheme.Bearer} $token")
        }
    }
    override suspend fun deactivateTicket(token: String, uid: String): String {
        return client.post {
            url(Routing.DEACTIVATE_TICKET)
            contentType(ContentType.Application.Json)
            parameter(Parameters.TICKET_QR , uid)
            header(Header.DRIVER_TOKEN, "${AuthScheme.Bearer} $token")
        }
    }
}