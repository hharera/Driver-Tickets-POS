package com.englizya.repository

import com.englizya.model.response.ManifestoDetails

interface ManifestoRepository {
    suspend fun getManifesto(): Result<ManifestoDetails>
}