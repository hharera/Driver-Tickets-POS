package com.englizya.api.impl

import androidx.compose.runtime.internal.composableLambdaInstance
import com.englizya.api.RemoteManifestoService
import com.englizya.api.utils.AuthenticationParameters
import com.englizya.api.utils.Routing
import com.englizya.datastore.LocalTicketPreferences
import com.englizya.model.request.EndShiftRequest
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ManifestoDetails
import com.englizya.model.response.ShiftReportResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class RemoteManifestoServiceImpl constructor(
    private val client: HttpClient,
    private val driverDataStore: LocalTicketPreferences,
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

    override suspend fun getManifesto(token: String): ManifestoDetails {
        return client.get {
            url(Routing.GET_MANIFESTO)
            headers.append(
                name = HttpHeaders.Authorization,
                value = "${AuthenticationParameters.BEARER} $token"
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

    override suspend fun getLongManifestoShiftReport(): LongManifestoEndShiftResponse {
        return client.get{
            url(Routing.LONG_MANIFESTO_END_SHIFT)
            headers.append(
                name = "driver_header",
                value = "${AuthenticationParameters.BEARER} ${driverDataStore.getToken()}"
            )

        }
    }
}