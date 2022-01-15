package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.DriverConstants
import com.englizya.datastore.utils.DriverConstants.DRIVER_CODE
import com.englizya.datastore.utils.DriverConstants.DRIVER_NAME
import com.englizya.datastore.utils.DriverConstants.DRIVER_USERNAME
import com.englizya.datastore.utils.Value.NULL_INT
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.datastore.utils.SourceConstants

class DriverDataStore(private val context: Context) {

    //TODO reformat
    //TODO clean
    //TODO remove imports

    private val driverSharedPreferences =
        context.getSharedPreferences(SourceConstants.DRIVER_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)

    fun setDriverCode(driverCode: Int) {
        driverSharedPreferences.edit().putInt(DriverConstants.DRIVER_CODE, driverCode).apply()
    }

    fun setToken(token: String) {
        driverSharedPreferences.edit().putString(DriverConstants.DRIVER_TOKEN, token).apply()
    }

    fun getToken() : String = driverSharedPreferences.getString(DriverConstants.DRIVER_TOKEN, NULL_STRING)!!

    fun setDriverName(driverName: String) {
        driverSharedPreferences.edit().putString(DriverConstants.DRIVER_NAME, driverName).apply()
    }

    fun setDriverUsername(driverUsername: String) {
        driverSharedPreferences.edit().putString(DRIVER_USERNAME, driverUsername).apply()
    }

    fun getDriverCode(): Int {
        return driverSharedPreferences.getInt(DRIVER_CODE, NULL_INT)
    }

    fun getDriverName(): String {
        return driverSharedPreferences.getString(DRIVER_NAME, NULL_STRING)!!
    }

    fun getDriverUsername(): String {
        return driverSharedPreferences.getString(DRIVER_USERNAME, NULL_STRING)!!
    }
}