package com.englizya.api

import com.englizya.model.request.SeatPriceRequest
import com.englizya.model.request.Ticket

interface SeatPriceService {
    suspend fun getSeatPrice(request: SeatPriceRequest): Double

}