package com.englizya.api.impl

import com.englizya.api.RemoteUserService
import com.englizya.api.utils.Routing.LOGIN
import com.englizya.model.request.LoginRequest
import com.englizya.model.response.LoginResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class RemoteUserServiceImpl constructor(
    private val client: HttpClient
) : RemoteUserService {

    override suspend fun login(request: LoginRequest): LoginResponse =
        client.post<LoginResponse> {
            url(LOGIN)
            contentType(ContentType.Application.Json)
            body = request
        }
}