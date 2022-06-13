package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserTicket(
    var tripName: String,
    val ticketId: Int,
    val ticketQr: String,
    var ticketingTime: String?,
    var seatNo: Int,
    var source: String,
    var sourceTime: String?,
    var destination: String,
    var destinationTime: String?,
    var serviceType: String,
    var uid: String,
    val bookingOfficeMovingTime: String?,
    val bookingOfficeRidingTime: String?,
    val bookingOfficeName: String,
    val reservationDate: String,
    val tripId: Int,
)

