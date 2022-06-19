package com.englizya.api

import com.englizya.model.request.Ticket
import com.englizya.model.request.TourismTicketsWithWalletRequest
import com.englizya.model.response.UserTicket

interface RemoteTicketService {
    suspend fun insertTicket(ticket: Ticket): Ticket
    suspend fun insertTickets(tickets: List<Ticket>): List<Ticket>
    suspend fun requestTickets(
        token: String,
        uid: String,
        quantity: Int,
        category: Int,
//        walletOtp: String,
//        latitude: Double?,
//        longitude: Double?,
    ): List<Ticket>

    suspend fun requestTourismTickets(
        token: String,
        uid: String,
        quantity: Int,
        sourceStationId: Int,
        destinationStationId: Int,
        tripId: Int,
    ): List<Ticket>

    suspend fun requestLongTicketsWithWallet(request: TourismTicketsWithWalletRequest): List<UserTicket>

     suspend fun requestReservedTicket(
        token: String,
        uid: String,
    ): UserTicket

    suspend fun deactivateTicket(
        token: String,
        uid: String,
    ): String
}