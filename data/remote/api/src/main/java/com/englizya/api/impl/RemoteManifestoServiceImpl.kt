package com.englizya.api.impl

import com.englizya.api.RemoteManifestoService
import com.englizya.api.utils.AuthenticationParameters
import com.englizya.api.utils.Routing
import com.englizya.datastore.core.DriverDataStore
import com.englizya.model.request.EndShiftRequest
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteManifestoServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val driverDataStore: DriverDataStore,
) : RemoteManifestoService {

    override suspend fun getManifesto(): ManifestoDetails {
        return client.get {
            url(Routing.GET_MANIFESTO)
            headers.append(
                name = HttpHeaders.Authorization,
                value = "${AuthenticationParameters.BEARER} ${driverDataStore.getToken()}"
            )
        }
    }

    override suspend fun getShiftReport(
        endShiftRequest: EndShiftRequest
    ): ShiftReportResponse {
        return client.post {
            url(Routing.END_SHIFT)
            contentType(ContentType.Application.Json)
            body = endShiftRequest
            headers.append(
                name = HttpHeaders.Authorization,
                value = "${AuthenticationParameters.BEARER} ${driverDataStore.getToken()}"
            )
        }
    }
}