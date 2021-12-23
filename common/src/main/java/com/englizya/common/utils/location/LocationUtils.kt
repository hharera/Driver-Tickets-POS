package com.englizya.common.utils.location

import android.location.Geocoder
import com.englizya.dwaa.utils.location.LocationConstants
import java.io.IOException

class LocationUtils {

    companion object {
        fun getLocationAddressName(map: Map<String, Double>, geocoder: Geocoder): String? {
            try {
                val addresses = geocoder.getFromLocation(
                    map[LocationConstants.latitude]!!,
                    map[LocationConstants.longitude]!!,
                    1
                )
                val address = addresses[0]
                return "${address.subAdminArea},${address.adminArea},${address.countryName}"
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
            return null
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