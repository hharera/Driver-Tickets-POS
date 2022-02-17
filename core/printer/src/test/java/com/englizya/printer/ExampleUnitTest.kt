package com.englizya.printer

import android.content.ContentValues.TAG
import android.util.Log
import com.englizya.common.utils.time.TimeUtils
import com.englizya.model.WorkTime
import com.englizya.model.response.ShiftReportResponse
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.log

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private  val START_SHIFT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private  val END_SHIFT_DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"
    private val TAG = "ExampleUnitTest"
    private val MILLIS_IN_SECOND = 1000
    private val MILLIS_IN_MINUTE = 60000
    private val MILLIS_IN_HOUR = 3600000
    private val MILLIS_IN_DAY = 86400000
    private val MILLIS_IN_WEEK = 604800000
    private val MILLIS_IN_MONTH = 2592000000
    private val MILLIS_IN_YEAR = 31536000000


    @Test
    fun `testing calculate work hours with date format`() {
        val startTime = SimpleDateFormat(START_SHIFT_DATE_FORMAT).parse("2022-02-15 09:51:23")
        val endTime = SimpleDateFormat(END_SHIFT_DATE_FORMAT).parse("15.02.2022 04:18:16")

        val millis = (endTime.time - startTime.time)
        val workTime = calculateWorkTime(millis)
        println(
            "${workTime.hours}"
                .plus(" ساعات ")
                .plus(" ، ")
                .plus(" ${workTime.minutes} ")
                .plus(" دقائق ")
        )
    }

    private fun calculateWorkTime(millis: Long): WorkTime {
        val years = calculateYears(millis)
        val months = calculateMonths(millis - (years * MILLIS_IN_YEAR))
        val weeks = calculateWeeks(millis - (months * MILLIS_IN_MONTH))
        val days = calculateDays(millis - (weeks * MILLIS_IN_WEEK))
        val hours = calculateHours(millis - days * MILLIS_IN_DAY)
        val minutes = calculateMinutes(millis - hours * MILLIS_IN_HOUR)
        val seconds = calculateSeconds(millis - minutes * MILLIS_IN_MINUTE)

        return WorkTime(
            years = years,
            months = months,
            weeks = weeks,
            days = days,
            hours = hours,
            minutes = minutes,
            seconds = seconds
        )
    }

    private fun calculateSeconds(millis: Long): Long {
        return millis / MILLIS_IN_SECOND
    }

    private fun calculateMinutes(millis: Long): Long {
        return millis / MILLIS_IN_MINUTE
    }

    private fun calculateHours(millis: Long): Long {
        return millis / MILLIS_IN_HOUR
    }

    private fun calculateDays(millis: Long): Long {
        return millis / MILLIS_IN_DAY
    }

    private fun calculateWeeks(millis: Long): Long {
        return millis / MILLIS_IN_WEEK
    }

    private fun calculateMonths(millis: Long): Long {
        return millis / MILLIS_IN_MONTH
    }

    private fun calculateYears(millis: Long): Long {
        return millis / MILLIS_IN_YEAR
    }
}