package com.englizya.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.englizya.model.request.Ticket
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class UnPrintedTicket(
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