package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.CarConstants.CAR_CODE
import com.englizya.datastore.utils.CarConstants.CAR_LINE_CODE
import com.englizya.datastore.utils.CarConstants.CAR_LINE_DESCRIPTION
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.datastore.utils.SourceConstants

class CarDataStore(context: Context) {

    private val carSharedPreferences =
        context.getSharedPreferences(SourceConstants.CAR_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun setCarCode(carCode: String) {
        carSharedPreferences.edit().putString(CAR_CODE, carCode).apply()
    }

    fun getCarCode(): String {
        return carSharedPreferences.getString(CAR_CODE, NULL_STRING)!!
    }

    fun setCarLineCode(carLineCode: String) {
        carSharedPreferences.edit().putString(CAR_LINE_CODE, carLineCode).apply()
    }

    fun getCarLineCode(): String {
        return carSharedPreferences.getString(CAR_LINE_CODE, NULL_STRING)!!
    }

    fun setCarLineDescription(carLineDescription: String) {
        carSharedPreferences.edit().putString(CAR_LINE_DESCRIPTION, carLineDescription).apply()
    }

    fun getCarLineDescription(): String {
        return carSharedPreferences.getString(CAR_LINE_DESCRIPTION, NULL_STRING)!!
    }

}