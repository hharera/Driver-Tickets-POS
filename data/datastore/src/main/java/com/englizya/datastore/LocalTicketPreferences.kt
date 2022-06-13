package com.englizya.datastore

import android.content.Context
import com.englizya.datastore.utils.*
import com.englizya.datastore.utils.Value.CUSTOMER_SUPPORT
import com.englizya.datastore.utils.Value.NULL_INT

class LocalTicketPreferences(context: Context) {

    private val companySharedPreferences =
        context.getSharedPreferences(SourceConstants.COMPANY_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)


    fun getCustomerSupportNumber() : String{
        return CUSTOMER_SUPPORT
    }

    private val carSharedPreferences =
        context.getSharedPreferences(SourceConstants.CAR_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun setCarCode(carCode: String) {
        carSharedPreferences.edit().putString(CarConstants.CAR_CODE, carCode).apply()
    }

    fun getCarCode(): String {
        return carSharedPreferences.getString(CarConstants.CAR_CODE, Value.NULL_STRING)!!
    }

    fun setCarLineCode(carLineCode: String) {
        carSharedPreferences.edit().putString(CarConstants.CAR_LINE_CODE, carLineCode).apply()
    }

    fun getCarLineCode(): String {
        return carSharedPreferences.getString(
            CarConstants.CAR_LINE_CODE,
            Value.NULL_STRING
        )!!
    }

    fun setCarLineDescription(carLineDescription: String) {
        carSharedPreferences.edit().putString(CarConstants.CAR_LINE_DESCRIPTION, carLineDescription).apply()
    }

    fun getCarLineDescription(): String {
        return carSharedPreferences.getString(
            CarConstants.CAR_LINE_DESCRIPTION,
            Value.NULL_STRING
        )!!
    }
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

    fun getToken() : String = driverSharedPreferences.getString(
        DriverConstants.DRIVER_TOKEN,
        Value.NULL_STRING
    )!!

    fun setDriverName(driverName: String) {
        driverSharedPreferences.edit().putString(DriverConstants.DRIVER_NAME, driverName).apply()
    }

    fun setDriverUsername(driverUsername: String) {
        driverSharedPreferences.edit().putString(DriverConstants.DRIVER_USERNAME, driverUsername).apply()
    }

    fun getDriverCode(): Int {
        return driverSharedPreferences.getInt(
            DriverConstants.DRIVER_CODE,
            Value.NULL_INT
        )
    }

    fun getDriverName(): String {
        return driverSharedPreferences.getString(
            DriverConstants.DRIVER_NAME,
            Value.NULL_STRING
        )!!
    }

    fun getDriverUsername(): String {
        return driverSharedPreferences.getString(
            DriverConstants.DRIVER_USERNAME,
            Value.NULL_STRING
        )!!
    }

    private val manifestoPreferences =
        context.getSharedPreferences(
            SourceConstants.MANIFESTO_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    fun setManifestoNo(manifestoNo: Int) {
        manifestoPreferences.edit().putInt(ManifestoConstants.MANIFESTO_NO, manifestoNo).apply()
    }

    fun getManifestoNo(): Int = manifestoPreferences.getInt(
        ManifestoConstants.MANIFESTO_NO,
        Value.NULL_INT
    )

    fun setManifestoYear(year: Int) {
        manifestoPreferences.edit().putInt(ManifestoConstants.MANIFESTO_YEAR, year).apply()
    }

    fun getManifestoYear(): Int = manifestoPreferences.getInt(
        ManifestoConstants.MANIFESTO_YEAR,
        Value.NULL_INT
    )

    fun setManifestoType(isShortManifesto: Int) {
        manifestoPreferences.edit().putInt(ManifestoConstants.MANIFESTO_TYPE, isShortManifesto).apply()
    }

    fun getManifestoType(): Int = manifestoPreferences.getInt(
        ManifestoConstants.MANIFESTO_TYPE,
        Value.NULL_INT
    )

    fun setManifestoDate(date: String) {
        manifestoPreferences.edit().putString(ManifestoConstants.MANIFESTO_DATE, date).apply()
    }

    fun getManifestoDate(): String = manifestoPreferences.getString(
        ManifestoConstants.MANIFESTO_DATE,
        Value.NULL_STRING
    )!!

    fun setIsManifestoShort(isManifestoShort: Int) {
        manifestoPreferences.edit().putInt(ManifestoConstants.IS_SHORT_MANIFESTO, isManifestoShort).apply()
    }

    fun getIsManifestoShort(): String = manifestoPreferences.getString(
        ManifestoConstants.IS_SHORT_MANIFESTO,
        Value.NULL_STRING
    )!!


    private val ticketSharedPreferences =
        context.getSharedPreferences(
            SourceConstants.TICKET_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    fun setTicketCategories(ticketCategories: Set<String>) {
        ticketSharedPreferences.edit()
            .putStringSet(TicketConstants.TICKET_CATEGORIES, ticketCategories).apply()
    }

    fun getTicketCategories(): Set<String>? {
        return ticketSharedPreferences.getStringSet(TicketConstants.TICKET_CATEGORIES, HashSet())
    }

    fun setTripId(trip: Int?) {
        trip?.let {
            ticketSharedPreferences.edit().putInt(TicketConstants.TRIP_ID, it).apply()
        }
    }

    fun getTripId(): Int {
        return ticketSharedPreferences.getInt(TicketConstants.TRIP_ID, NULL_INT)
    }

    fun setReservationId(reservationId: Int?) {
        reservationId?.let {
            ticketSharedPreferences.edit().putInt(
                TicketConstants.RESERVATION_ID,
                it
            ).apply()
        }
    }

    fun getReservationId(): Int {
        return ticketSharedPreferences.getInt(TicketConstants.RESERVATION_ID, NULL_INT)
    }
}