package com.englizya.model.request

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Ticket(
    @PrimaryKey
    var ticketId: String,
    var lineCode: String,
    var driverCode: Int,
    var carCode: String,
    var time: String,
    var paymentWay: String,
    var ticketCategory: Int,
    var manifestoId: Int,
    var manifestoYear: Int,
    var ticketLongitude: Int? = null,
    var ticketLatitude: Int? = null,
)