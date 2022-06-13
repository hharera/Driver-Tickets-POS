package com.englizya.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LineCategory (
    var ticketCategory: Int,
    var lineCode: String,
)
