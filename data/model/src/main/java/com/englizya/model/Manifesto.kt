package com.englizya.model

import kotlinx.serialization.Serializable

@Serializable
data class Manifesto(
    var manifestoId: Int,
    var year: Int,
    var lineCode: String,
    var carCode: String,
    var driverCode: Int,
    var date: String,
    var timeOut: String? = null,
    var timeIn: String? = null,
)