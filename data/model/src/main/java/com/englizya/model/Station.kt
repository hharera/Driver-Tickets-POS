package com.englizya.model

import kotlinx.serialization.Serializable

@Serializable
data class Station(
    var branchId: Int,
    var branchName: String? = null,
    var areaId: Int? = null,
    var bookingOfficeList: List<BookingOffice>? = null,
)