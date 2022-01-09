package com.englizya.repository

import com.englizya.model.Manifesto
import com.englizya.model.request.LoginRequest
import com.englizya.model.response.LoginResponse

interface ManifestoRepository {
    suspend fun getManifesto(): Result<Manifesto>
}