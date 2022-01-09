package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val jwt: String)