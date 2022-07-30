package io.philippeboisney.common_test.data

import com.englizya.model.response.ShiftReportResponse
import org.joda.time.DateTime

val report = ShiftReportResponse(
    "Dummy",
    125,
    "Dummy",
    "Dummy",
    DateTime.now().toString("yyyy-MM-dd hh:mm:ss"),
    125,
    125,
    125,
    125,
    DateTime.now().toString("yyyy-MM-dd hh:mm:ss"),
    DateTime.now().toString("yyyy.MM.dd hh:mm:ss"),
    125,
    125,
)