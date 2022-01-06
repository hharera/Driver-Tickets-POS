package com.englizya.repository

import com.englizya.model.request.LoginRequest
import com.englizya.model.response.LoginResponse

interface UserRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
}