package com.englizya.model.request

data class Ticket(
    var ticketId: String,
    var lineCode: String,
    var driverCode: String,
    var carCode: String,
    var time: String,
    var date: String,
    var paymentWay: String,
    var ticketCategory: Int,
    var manifestoId: Int,
    var manifestoYear: Int,
    var ticketLongitude: Int? = null,
    var ticketLatitude: Int? = null,
)