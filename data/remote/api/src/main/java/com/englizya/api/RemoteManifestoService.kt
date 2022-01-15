package com.englizya.api

import com.englizya.model.request.EndShiftRequest
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse

interface RemoteManifestoService {
    suspend fun getManifesto(): ManifestoDetails
    suspend fun getShiftReport(endShiftRequest: EndShiftRequest): ShiftReportResponse
}