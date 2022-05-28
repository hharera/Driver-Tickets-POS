package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineStation(
    @SerialName("stationOrder") var stationOrder: Int? = null,
    @SerialName("lineId") var lineId: Int? = null,
    @SerialName("pathType") var pathType: Int? = null,
    @SerialName("lineIcon") var lineIcon: String? = null,
    @SerialName("branch") var branch: Branch? = Branch()
)