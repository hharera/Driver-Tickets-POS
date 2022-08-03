package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LongManifestoEndShiftResponse(
    val driverCode: Int,
    val lineCode: String,
    val carCode: String,
    val manifestoDate: String,
    val shiftStartTime: String,
    val shiftEndTime: String,
    val workSeconds: Long,
    val ticketDetails: HashMap<Double, Int>,
    val totalTickets: Int,
    val totalIncome: Double,
)
