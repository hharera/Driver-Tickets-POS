package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Plan(
    @SerialName("planId") var planId: Int? = null,
    @SerialName("lineId") var lineId: Int? = null,
    @SerialName("pathType") var pathType: Int? = null,
    @SerialName("seatPrices") var seatPrices: ArrayList<SeatPrices> = arrayListOf()
)