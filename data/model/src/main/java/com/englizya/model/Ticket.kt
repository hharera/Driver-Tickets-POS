package com.englizya.model

import androidx.room.PrimaryKey

abstract class Ticket {
    abstract var ticketId: String
    abstract var lineCode: String
    abstract var driverCode: Int
    abstract var carCode: String
    abstract var time: String
    abstract var paymentWay: String
    abstract var ticketCategory: Int
    abstract var manifestoId: Int
    abstract var manifestoYear: Int
    abstract var ticketLongitude: Int?
    abstract var ticketLatitude: Int?
}