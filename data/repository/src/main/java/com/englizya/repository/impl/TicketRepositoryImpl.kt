package com.englizya.repository.impl

import com.englizya.api.RemoteTicketService
import com.englizya.local.TicketDao
import com.englizya.model.UnPrintedTicket
import com.englizya.model.request.Ticket
import com.englizya.model.request.TourismTicketsWithWalletRequest
import com.englizya.model.response.UserTicket
import com.englizya.repository.TicketRepository


class TicketRepositoryImpl constructor(
    private val ticketService: RemoteTicketService,
    private val ticketDao: TicketDao,
) : TicketRepository {

    override suspend fun insertTicket(
        ticket: Ticket,
        forceOnline: Boolean
    ): Result<Unit> =
        kotlin.runCatching {
            if (forceOnline) {
                ticketService.insertTicket(ticket)
            } else {
                ticketDao.insertTicket(ticket)
            }
        }

    override suspend fun insertUnPrintedTicket(ticket: Ticket): Result<Unit> =
        kotlin.runCatching {
            ticketDao.insertUnPrintedTicket(
                UnPrintedTicket(
                    ticketCategory = ticket.ticketCategory,
                    carCode = ticket.carCode,
                    driverCode = ticket.driverCode,
                    lineCode = ticket.lineCode,
                    manifestoId = ticket.manifestoId,
                    manifestoYear = ticket.manifestoYear,
                    paymentWay = ticket.paymentWay,
                    ticketId = ticket.ticketId,
                    ticketLatitude = ticket.ticketLatitude,
                    ticketLongitude = ticket.ticketLongitude,
                    time = ticket.time
                )
            )
        }

    override suspend fun insertTickets(
        tickets: List<Ticket>,
        forceOnline: Boolean
    ): Result<Unit> =
        kotlin.runCatching {
            if (forceOnline) {
                ticketService.insertTickets(tickets)
            } else {
                ticketDao.insertTickets(tickets)
            }
        }

    override suspend fun getLocalTickets(): Result<List<Ticket>> = kotlin.runCatching {
        ticketDao.getTickets()
    }

    override suspend fun deleteLocalTickets(): Result<Unit> = kotlin.runCatching {
        ticketDao.deleteAll()
    }

    override fun getAllUnPrintedTickets(): Result<List<UnPrintedTicket>> =
        kotlin.runCatching {
            ticketDao.getAllSavedTickets()
        }

    override suspend fun requestTickets(
        token: String,
        uid: String,
        quantity: Int,
        selectedCategory: Int,
//        walletOtp: String,
//        latitude: Double?,
//        longitude: Double?,
    ): Result<List<Ticket>> =
        kotlin.runCatching {
            ticketService.requestTickets(
                token,
                uid,
                quantity,
                selectedCategory
//                walletOtp,
//                latitude,
//                longitude

            )
        }

    override suspend fun requestTourismTickets(
        token: String,
        uid: String,
        quantity: Int,
        sourceStationId: Int,
        destinationStationId: Int,
        tripId: Int
    ): Result<List<Ticket>> =
        kotlin.runCatching {
            ticketService.requestTourismTickets(
                token,
                uid,
                quantity,
                sourceStationId,
                destinationStationId,
                tripId,


                )
        }

    override suspend fun requestLongTicketsWithWallet(request: TourismTicketsWithWalletRequest): Result<List<UserTicket>> =
        kotlin.runCatching {
            ticketService.requestLongTicketsWithWallet(request)
        }

    override suspend fun requestReservedTicket(
        token: String,
        uid: String,
    ): Result<UserTicket> = kotlin.runCatching {
        ticketService.requestReservedTicket(token , uid)
    }

}