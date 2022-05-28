package com.englizya.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    @SerialName("tripId") var tripId: Int? = null,
    @SerialName("tripName") var tripName: String? = null,
    @SerialName("startDate") var startDate: String? = null,
    @SerialName("endDate") var endDate: String? = null,
    @SerialName("tripDays") var tripDays: String? = null,
    @SerialName("pathType") var pathType: Int? = null,
    @SerialName("setNumber") var setNumber: String? = null,
    @SerialName("lineId") var lineId: Int? = null,
    @SerialName("stations") var stations: ArrayList<LineStation> = arrayListOf(),
    @SerialName("tripTimes") var tripTimes: ArrayList<LineStationTime> = arrayListOf(),
    @SerialName("plan") var plan: Plan? = Plan(),
    @SerialName("reservations") var reservations: ArrayList<Reservations> = arrayListOf(),
    @SerialName("service") var service: ServiceDegree? = null
)