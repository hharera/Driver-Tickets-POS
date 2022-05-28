package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Seat(
    @SerialName("tripReservationId") var tripReservationId: Int? = null,
    @SerialName("seatId") var seatId: Int? = null,
    @SerialName("closeFlag") var closeFlag: Int? = null,
    @SerialName("seatStatus") var seatStatus: String? = null
)