package com.englizya.repository

import com.englizya.model.request.SeatPriceRequest
import com.englizya.model.request.Ticket

interface SeatPricingRepository {
    suspend fun getSeatPrice(request:SeatPriceRequest) : Result<Double>

}