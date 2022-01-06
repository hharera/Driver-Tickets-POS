package com.englizya.api

import com.englizya.model.request.LoginRequest
import com.englizya.model.response.LoginResponse

interface RemoteUserService {
    suspend fun login(request: LoginRequest): LoginResponse
}