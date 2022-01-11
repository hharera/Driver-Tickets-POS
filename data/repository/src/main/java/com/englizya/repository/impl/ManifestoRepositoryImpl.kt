package com.englizya.repository.impl

import com.englizya.api.RemoteManifestoService
import com.englizya.model.response.ManifestoDetails
import com.englizya.repository.ManifestoRepository
import javax.inject.Inject

class ManifestoRepositoryImpl @Inject constructor(
    private val manifestoService: RemoteManifestoService
) : ManifestoRepository {

    override suspend fun getManifesto(): Result<ManifestoDetails> = kotlin.runCatching {
        manifestoService.getManifesto()
    }
}