package com.englizya.repository.impl

import com.englizya.api.BookingInformationService
import com.englizya.model.response.BookingReportResponse
import com.englizya.repository.BookingInformationRepository

class BookingInformationRepositoryImpl constructor(
    private val bookingInformationService: BookingInformationService

): BookingInformationRepository{
    override suspend fun getBookingReport(token : String): Result<List<BookingReportResponse>> = kotlin.runCatching {
        bookingInformationService.getBookingReport(token)
    }
}