package com.englizya.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ManifestoDetails(
    @SerialName("lineCategory")
    var ticketCategory: Int,
    var incomeValue: Int?,
    var manifestoId: Int,
    var year: Int,
    var lineCode: String,
    var carCode: String,
    var driverCode: Int,
    var date: String,
    var timeOut: String? = null,
    var timeIn: String? = null,
)