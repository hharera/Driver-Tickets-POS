package com.englizya.common.utils.time

import org.joda.time.DateTime
import java.text.SimpleDateFormat

object TimeUtils {

    private val TIME_START = DateTime(2022, 1, 1, 0, 0)
    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val MILLIS_IN_HOURS = 3600000

    fun getTicketTimeMillis(): Long = DateTime.now().minus(TIME_START.millis).millis

    fun calculateWorkHours(startTime: String, endTime: String): String {
        val startTime = SimpleDateFormat(DATE_FORMAT).parse(startTime)
        val endTime = SimpleDateFormat(DATE_FORMAT).parse(endTime)

        val millis = endTime.time - startTime.time
        return calculateHours(millis).toString()
    }

    private fun calculateHours(millis: Long): Long {
        return millis / MILLIS_IN_HOURS
    }
}