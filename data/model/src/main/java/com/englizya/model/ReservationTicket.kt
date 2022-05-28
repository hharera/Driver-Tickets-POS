package com.englizya.model

import kotlinx.serialization.Serializable


@Serializable
class ReservationTicket {
    var reservationId: Int? = null
    var seatNo: Int? = null
    var ticketNo: Int? = null
    var officeBooking: Int? = null
    var source: Int? = null
    var destination: Int? = null
    var ticketTypeId: Int? = null
    var ticketValue: Int? = null
    var officeBookingId: Int? = null
    var passengerPhoneNumber: String? = null
    var passengerName: String? = null
    var passengerNationalId: String? = null
    var reservationTicketId: Int? = null
    var fromLineNo: Int? = null
    var toLineNo: Int? = null

    constructor(
        reservationId: Int,
        seatNo: Int,
        source: Int,
        destination: Int,
        passengerPhoneNumber: String,
        passengerName: String
    ) {
        this.reservationId = reservationId
        this.seatNo = seatNo
        this.source = source
        this.destination = destination
        this.passengerPhoneNumber = passengerPhoneNumber
        this.passengerName = passengerName
    }
}

