package com.englizya.api.impl

import com.englizya.api.StationService
import com.englizya.api.utils.Routing
import com.englizya.model.Station
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class StationServiceImpl constructor(
    private val client: HttpClient
) : StationService {

    override suspend fun getAllStations(): List<Station> =
        client.get(Routing.GET_ALL_STATIONS) {
            contentType(ContentType.Application.Json)

        }
}