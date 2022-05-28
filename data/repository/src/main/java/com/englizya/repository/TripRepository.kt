package com.englizya.repository

import com.englizya.api.TripService
import com.englizya.model.Trip
import javax.inject.Inject

interface TripRepository {
    suspend fun getTrip(tripId : Int, driverToken : String): Result<Trip>
}

class TripRepositoryImpl @Inject constructor(
    private val tripService: TripService
) : TripRepository {

    override suspend fun getTrip(tripId : Int, driverToken : String): Result<Trip> = kotlin.runCatching {
        tripService.getTrip(tripId, driverToken)
    }
}