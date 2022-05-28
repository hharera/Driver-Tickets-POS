package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class WalletDetails(
    val balance: Double,
    val name: String,
    val phoneNumber: String,
)
