package com.englizya.model.request

import org.joda.time.DateTime

data class TicketRequest(
    var ticketSerial: String,
    var time: DateTime,
    var driverCode: Int,
    var carCode: Int,
    var lineCode: Int,
    var ticketCategory: Int,
)
