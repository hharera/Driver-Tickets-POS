package com.englizya.api.impl

import com.englizya.api.SeatPriceService
import com.englizya.api.utils.Header
import com.englizya.api.utils.Parameters
import com.englizya.api.utils.Routing
import com.englizya.model.request.SeatPriceRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*

class SeatPriceServiceImpl constructor(
    private val client: HttpClient,
) : SeatPriceService {
    override suspend fun getSeatPrice(request: SeatPriceRequest): Double {
        return client.get{
            url(Routing.GET_SEAT_PRICE)
            parameter(Parameters.SOURCE , request.sourceBranchId)
            parameter(Parameters.DESTINATION , request.destinationBranchId)
            parameter(Parameters.TRIP_ID , request.tripId)
            timeout {
                requestTimeoutMillis = 100000
            }
        }
    }
}