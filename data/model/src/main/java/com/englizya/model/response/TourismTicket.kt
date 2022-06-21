package com.englizya.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class TourismTicket(
    @PrimaryKey
    var ticketId: String,
    var source: String,
    var destination: String,
    var serviceDegree: String,
    var seatNo: Int,
    var tripId: Int,
    var time: String,
    var paymentWay: String,
    var ticketCategory: Int,
    var manifestoId: Int,
    var manifestoYear: Int,
    var ticketLongitude: Double? = null,
    var ticketLatitude: Double? = null,
)