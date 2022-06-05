package com.englizya.repository

import com.englizya.model.Station

interface StationRepository {

    suspend fun getAllStations(): Result<List<Station>>
}