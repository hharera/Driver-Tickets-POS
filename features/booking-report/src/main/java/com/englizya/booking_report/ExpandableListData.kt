package com.englizya.booking_report

import android.util.Log
import com.englizya.model.response.BookingReportResponse
import kotlin.collections.ArrayList

internal object ExpandableListData {
    var tripName:
            List<String> = emptyList()
        get() {
            return field
        }
    var officeName:
            List<String> = emptyList()
        get() {
            return field
        }

    var ticketsCount:
            MutableList<Int> = ArrayList()
        get() {
            return field
        }


    fun setData(tripList: List<BookingReportResponse>?) {
        val officeNameList: MutableList<String> = ArrayList()
        val tripNameList: MutableList<String> = ArrayList()
        val ticketsCountList: MutableList<Int> = ArrayList()
        tripList?.forEach { trip ->
            tripNameList.add(trip.tripName)
            trip.officeListReport.forEach {
                officeNameList.add(it.officeName)
                ticketsCountList.add(it.ticketsCount)
            }


        }
        officeNameList.forEach {
            Log.d("Office" ,it )

        }
        tripNameList.forEach {
            Log.d("tripName" ,it )

        }
        ticketsCountList.forEach {
            Log.d("Office" ,it.toString() )

        }
        officeName = officeNameList
        tripName = tripNameList
        ticketsCount = ticketsCountList

    }
}

