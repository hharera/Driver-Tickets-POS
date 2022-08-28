package com.englizya.api.impl

import com.englizya.api.BookingInformationService
import com.englizya.api.utils.AuthenticationParameters
import com.englizya.api.utils.Routing
import com.englizya.model.response.BookingReportResponse
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class BookingInformationServiceImpl constructor(
    private val client: HttpClient,

    ) : BookingInformationService {
    override suspend fun getBookingReport(token: String): List<BookingReportResponse> {
        return client.get {
            url(Routing.GET_BOOKING_REPORT)
            headers.append(
                name = "driver_token",
                value = "${AuthenticationParameters.BEARER} $token"
            )
        }
    }
}