package com.englizya.common.utils.location

import android.location.Geocoder
import java.io.IOException

class LocationUtils {

    companion object {
        fun getLocationAddressName(map: Map<String, Double>, geocoder: Geocoder, results : Int = 1): Result<List<String>> =
            kotlin.runCatching {
                geocoder.getFromLocation(
                    map[LocationConstants.latitude]!!,
                    map[LocationConstants.longitude]!!,
                    results
                ).map { address ->
                    "${address.subAdminArea}, ${address.adminArea}, ${address.countryName}"
                }
            }

        fun getLocationAddressName(
            latitude: Double,
            longitude: Double,
            geocoder: Geocoder
        ): String? {
            try {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                val address = addresses[0]
                return "${address.subAdminArea},${address.adminArea},${address.countryName}"
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
            return null
        }
    }
}