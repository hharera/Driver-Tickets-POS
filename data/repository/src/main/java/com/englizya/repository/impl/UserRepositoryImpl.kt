package com.englizya.repository.impl

import com.englizya.api.RemoteUserService
import com.englizya.model.request.LoginRequest
import com.englizya.model.response.LoginResponse
import com.englizya.repository.UserRepository

class UserRepositoryImpl constructor(
    private val userService: RemoteUserService
) : UserRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> =
        runCatching {
            userService.login(request)
        }
}