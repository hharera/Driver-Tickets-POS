package com.englizya.repository

import com.englizya.api.TripService
import com.englizya.model.Trip

interface TripRepository {
    suspend fun getTrip(tripId: Int, driverToken: String): Result<Trip>
}

class TripRepositoryImpl constructor(
    private val tripService: TripService
) : TripRepository {

    override suspend fun getTrip(tripId: Int, driverToken: String): Result<Trip> =
        kotlin.runCatching {
            tripService.getTrip(tripId, driverToken)
        }
}