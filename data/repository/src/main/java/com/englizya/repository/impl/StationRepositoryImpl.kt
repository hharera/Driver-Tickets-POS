package com.englizya.repository.impl

import com.englizya.api.StationService
import com.englizya.model.Station
import com.englizya.repository.StationRepository

class StationRepositoryImpl constructor(
    private val stationService: StationService
) : StationRepository {

    override suspend fun getAllStations(): Result<List<Station>> = kotlin.runCatching {
        stationService.getAllStations()
    }

}