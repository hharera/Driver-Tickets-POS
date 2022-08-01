package com.englizya.repository.impl

import com.englizya.api.SeatPriceService
import com.englizya.api.StationService
import com.englizya.model.request.SeatPriceRequest
import com.englizya.repository.SeatPricingRepository

class SeatPricingRepositoryImpl constructor(
    private val seatPriceService:SeatPriceService


) : SeatPricingRepository {
    override suspend fun getSeatPrice(request: SeatPriceRequest): Result<Double> =kotlin.runCatching {
        seatPriceService.getSeatPrice(request)
    }
}