package com.englizya.model.response

import com.englizya.model.response.UserTicket
import kotlinx.serialization.Serializable

@Serializable
data class ReservedTicketResponse(
    val message : String,
    val data : UserTicket?,
    val status : String
)
