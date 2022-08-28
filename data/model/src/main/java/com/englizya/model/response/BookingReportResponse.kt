package com.englizya.model.response

import com.englizya.model.OfficeListReport
import kotlinx.serialization.Serializable

@Serializable

data class BookingReportResponse (
    var tripId: Int,
    var tripName : String ,
    var officeListReport : List<OfficeListReport>

    )