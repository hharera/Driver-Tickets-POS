package com.englizya.api

import com.englizya.api.utils.Header
import com.englizya.api.utils.Parameters
import com.englizya.api.utils.Routing
import com.englizya.model.response.WalletDetails
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*
import javax.inject.Inject

interface WalletService {
    suspend fun getWalletDetails(driverToken: String, uid: String): WalletDetails
}


class WalletServiceImpl @Inject constructor(
    private val client: HttpClient
) : WalletService {
    override suspend fun getWalletDetails(driverToken: String, uid: String): WalletDetails {
        return client.get(Routing.GET_WALLET_DETAILS) {
            header(Header.DRIVER_TOKEN,"${AuthScheme.Bearer} $driverToken")
            parameter(Parameters.UID, uid)
        }
    }
}