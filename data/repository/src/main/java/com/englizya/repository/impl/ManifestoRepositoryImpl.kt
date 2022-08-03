package com.englizya.repository.impl

import com.englizya.api.RemoteManifestoService
import com.englizya.model.request.EndShiftRequest
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse
import com.englizya.repository.ManifestoRepository

class ManifestoRepositoryImpl constructor(
    private val manifestoService: RemoteManifestoService
) : ManifestoRepository {

    override suspend fun getManifesto(): Result<ManifestoDetails> = kotlin.runCatching {
        manifestoService.getManifesto()
    }

    override suspend fun getManifesto(token: String): Result<ManifestoDetails> = kotlin.runCatching {
        manifestoService.getManifesto(token)
    }

    override suspend fun getShiftReport(endShiftRequest: EndShiftRequest): Result<ShiftReportResponse> = kotlin.runCatching {
        manifestoService.getShiftReport(endShiftRequest)
    }

    override suspend fun getLongManifestoShiftReport(): Result<LongManifestoEndShiftResponse> =kotlin.runCatching {
        manifestoService.getLongManifestoShiftReport()
    }

}