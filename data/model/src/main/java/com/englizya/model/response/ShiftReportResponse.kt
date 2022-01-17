package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ShiftReportResponse(
    val driverName: String,
    val driverCode: Int,
    val lineCode: String,
    val carCode: String,
    val date: String,
    val totalTickets: Int,
    val cash: Int,
    val qr: Int,
    val card: Int,
    val startTime: String,
    val endTime: String,
    val ticketCategory: Int,
    val totalIncome: Int,
)