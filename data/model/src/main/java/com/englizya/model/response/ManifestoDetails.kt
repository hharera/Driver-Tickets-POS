package com.englizya.model.response

import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class ManifestoDetails(
    var isShortManifesto: Int,
    var lineCategory: List<Int>,
    var incomeValue: Int?,
    var manifestoId: Int,
    var year: Int,
    var lineCode: String,
    var carCode: String,
    var driverCode: Int,
    var tripId: Int? = null,
    var reservationId: Int? = null,
    var date: String,
    var timeOut: String? = null,
    var timeIn: String? = null,
)