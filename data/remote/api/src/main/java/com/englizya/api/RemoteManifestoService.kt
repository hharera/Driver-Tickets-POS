package com.englizya.api

import com.englizya.model.Manifesto
import com.englizya.model.response.LoginResponse

interface RemoteManifestoService {
    suspend fun getManifesto(): Manifesto
}