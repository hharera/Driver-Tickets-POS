package com.englizya.model

import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import java.util.*

@Serializable
data class PrintableTicket(
    var tripName: String,
    val ticketId: Int,
    val ticketQr: String,
    var ticketingTime: String,
    var seatNo: Int,
    var source: String ,
    var destination: String,
    var serviceDegree: String,
    var ticketPrice: Double,
)