package com.englizya.repository

import com.englizya.model.request.EndShiftRequest
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse

interface ManifestoRepository {
    suspend fun getManifesto(): Result<ManifestoDetails>
    suspend fun getShiftReport(endShiftRequest: EndShiftRequest) : Result<ShiftReportResponse>
}