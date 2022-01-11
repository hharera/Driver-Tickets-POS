package com.englizya.api

import com.englizya.model.response.ManifestoDetails

interface RemoteManifestoService {
    suspend fun getManifesto(): ManifestoDetails
}