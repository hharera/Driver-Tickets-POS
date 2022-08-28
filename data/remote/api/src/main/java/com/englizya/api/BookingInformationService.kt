package com.englizya.api

import com.englizya.model.response.BookingReportResponse

interface BookingInformationService {
    suspend fun getBookingReport(token : String):List<BookingReportResponse>
}