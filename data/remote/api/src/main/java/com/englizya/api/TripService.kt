package com.englizya.api

import com.englizya.api.utils.Routing
import com.englizya.model.Trip
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

interface TripService {
    suspend fun getTrip(tripId: Int, driverToken: String): Trip
}

class TripServiceImpl @Inject constructor(
    private val client: HttpClient
) : TripService {

    override suspend fun getTrip(tripId: Int, driverToken: String): Trip =
        client.get {
            url(Routing.GET_TRIP)
            contentType(ContentType.Application.Json)
        }
}