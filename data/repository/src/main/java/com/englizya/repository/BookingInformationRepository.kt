package com.englizya.repository

import com.englizya.model.request.SeatPriceRequest
import com.englizya.model.response.BookingReportResponse

interface BookingInformationRepository {
    suspend fun getBookingReport(token:String) : Result<List<BookingReportResponse>>

}