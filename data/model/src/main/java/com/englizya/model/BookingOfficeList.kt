package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingOfficeList(
    @SerialName("officeId") var officeId: Int? = null,
    @SerialName("officeName") var officeName: String? = null
)