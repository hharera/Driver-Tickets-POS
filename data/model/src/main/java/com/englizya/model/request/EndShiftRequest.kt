package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EndShiftRequest(
    val year: Int,
    val manifestoId: Int,
)
