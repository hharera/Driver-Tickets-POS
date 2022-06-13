package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TourismTicketsWithWalletRequest(
    var driverAuthHeader: String,
    var reservationId: Int,
    var tripId: Int,
    var uid: String,
    var sourceStationId: Int,
    var destinationStationId: Int,
    var quantity: Int,
)