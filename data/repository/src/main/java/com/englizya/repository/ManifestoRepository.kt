package com.englizya.repository

import com.englizya.model.request.EndShiftRequest
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse

interface ManifestoRepository {
    suspend fun getManifesto(): Result<ManifestoDetails>
    suspend fun getShiftReport(endShiftRequest: EndShiftRequest) : Result<ShiftReportResponse>
    suspend fun getLongManifestoShiftReport() : Result<LongManifestoEndShiftResponse>

    suspend fun getManifesto(token: String): Result<ManifestoDetails>

}