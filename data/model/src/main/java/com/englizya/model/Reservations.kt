package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reservations(
    @SerialName("id") var id: Int? = null,
    @SerialName("tripId") var tripId: Int? = null,
    @SerialName("lineId") var lineId: Int? = null,
    @SerialName("date") var date: String? = null,
    @SerialName("destination") var destination: Int? = null,
    @SerialName("source") var source: Int? = null,
    @SerialName("seats") var seats: List<Seat> = arrayListOf()
)