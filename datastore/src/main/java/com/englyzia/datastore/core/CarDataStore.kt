package com.englyzia.datastore.core

import android.content.Context
import com.englyzia.datastore.base.CarConstants.CAR_CODE
import com.englyzia.datastore.base.CarConstants.CAR_LINE_CODE
import com.englyzia.datastore.base.CarConstants.CAR_LINE_DESCRIPTION
import com.englyzia.datastore.base.GeneralConstants.NULL_INT
import com.englyzia.datastore.base.GeneralConstants.NULL_STRING
import com.englyzia.datastore.base.SourceConstants

class CarDataStore(private val context: Context) {

    private val carSharedPreferences =
        context.getSharedPreferences(SourceConstants.CAR_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun setCarCode(carCode : Int) {
        carSharedPreferences.edit().putInt(CAR_CODE, carCode).apply()
    }

    fun setCarLineCode(carLineCode: Int) {
        carSharedPreferences.edit().putInt(CAR_LINE_CODE, carLineCode).apply()
    }

    fun setCarLineDescription(carLineDescription: String) {
        carSharedPreferences.edit().putString(CAR_LINE_DESCRIPTION, carLineDescription).apply()
    }

    fun getCarCode(): Int {
        return carSharedPreferences.getInt(CAR_CODE, NULL_INT)
    }

    fun getCarLineCode(): Int {
        return carSharedPreferences.getInt(CAR_LINE_CODE, NULL_INT)
    }

    fun getCarLineDescription(): String {
        return carSharedPreferences.getString(CAR_LINE_DESCRIPTION, NULL_STRING)!!
    }

}