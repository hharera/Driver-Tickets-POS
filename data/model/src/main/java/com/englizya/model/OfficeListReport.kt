package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfficeListReport(
    @SerialName("officeId") var officeId: Int,
    @SerialName("officeName") var officeName: String,
    @SerialName("ticketsCount") var ticketsCount: Int,


    )
