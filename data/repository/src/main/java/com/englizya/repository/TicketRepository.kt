package com.englizya.repository

import com.englizya.model.UnPrintedTicket
import com.englizya.model.request.Ticket
import com.englizya.model.request.TourismTicketsWithWalletRequest
import com.englizya.model.response.UserTicket


interface TicketRepository {

    suspend fun insertTicket(ticket : Ticket, forceOnline : Boolean) : Result<Unit>
    suspend fun insertUnPrintedTicket(ticket : Ticket) : Result<Unit>
    suspend fun insertTickets(tickets : List<Ticket>, forceOnline : Boolean) : Result<Unit>
    suspend fun getLocalTickets(): Result<List<Ticket>>
    suspend fun deleteLocalTickets(): Result<Unit>
    fun getAllUnPrintedTickets(): Result<List<UnPrintedTicket>>
    suspend fun requestTickets(
        token: String,
        uid: String,
        quantity: Int,
        selectedCategory: Int,
        walletOtp: String,
        latitude: Double?,
        longitude: Double?,
    ): Result<List<Ticket>>

    suspend fun requestTourismTickets(
        token: String,
        uid: String,
        quantity: Int,
        sourceStationId: Int,
        destinationStationId: Int,
        tripId: Int,
    ): Result<List<Ticket>>

    suspend fun requestLongTicketsWithWallet(request: TourismTicketsWithWalletRequest): Result<List<UserTicket>>
}