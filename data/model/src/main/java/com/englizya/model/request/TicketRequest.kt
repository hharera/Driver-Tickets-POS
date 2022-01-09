package com.englizya.model.request

import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class TicketRequest(
    var ticketSerial: String,
    var time: String,
    var driverCode: Int,
    var carCode: Int,
    var lineCode: Int,
    var ticketCategory: Int,
)
