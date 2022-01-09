package com.englizya.api.impl

import com.englizya.api.RemoteManifestoService
import com.englizya.api.utils.AuthenticationParameters
import com.englizya.api.utils.Routing
import com.englizya.datastore.core.DriverDataStore
import com.englizya.model.Manifesto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RemoteManifestoServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val driverDataStore: DriverDataStore,
) : RemoteManifestoService {

    override suspend fun getManifesto(): Manifesto {
        return client.get {
            url(Routing.GET_MANIFESTO)
            headers.append(
                name = HttpHeaders.Authorization,
                value = "${AuthenticationParameters.BEARER} ${driverDataStore.getToken()}"
            )
        }
    }
}