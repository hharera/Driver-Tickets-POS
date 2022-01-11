package com.englizya.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    var driverCode: Int,
    var password: String,
)
