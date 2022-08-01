package com.englizya.model.request

data class SeatPriceRequest (
    val tripId : Int,
    val sourceBranchId : Int,
    val destinationBranchId : Int

        )