package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeatPrices(
    @SerialName("planId") var planId: Int? = null,
    @SerialName("source") var source: Int? = null,
    @SerialName("destination") var destination: Int? = null,
    @SerialName("vipPrice") var vipPrice: Int? = null
)