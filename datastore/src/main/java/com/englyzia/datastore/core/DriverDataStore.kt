package com.englyzia.datastore.core

import android.content.Context
import com.englyzia.datastore.base.DriverConstants
import com.englyzia.datastore.base.DriverConstants.DRIVER_CODE
import com.englyzia.datastore.base.DriverConstants.DRIVER_NAME
import com.englyzia.datastore.base.DriverConstants.DRIVER_USERNAME
import com.englyzia.datastore.base.GeneralConstants.NULL_INT
import com.englyzia.datastore.base.GeneralConstants.NULL_STRING
import com.englyzia.datastore.base.SourceConstants

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