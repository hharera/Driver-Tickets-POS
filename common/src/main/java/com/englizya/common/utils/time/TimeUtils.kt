package com.englizya.common.utils.time

import com.englizya.model.WorkTime
import com.englizya.model.response.LongManifestoEndShiftResponse
import com.englizya.model.response.ShiftReportResponse
import org.joda.time.DateTime
import java.text.SimpleDateFormat

object TimeUtils {

    private val TIME_START = DateTime(2022, 1, 1, 0, 0)
    private const val START_SHIFT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"
    private const val END_SHIFT_DATE_FORMAT = "dd.MM.yyyy hh:mm:ss"
    private const val MILLIS_IN_SECOND = 1000
    private const val MILLIS_IN_MINUTE = 60000
    private const val MILLIS_IN_HOUR = 3600000
    private const val MILLIS_IN_DAY = 86400000
    private const val MILLIS_IN_WEEK = 604800000
    private const val MILLIS_IN_MONTH = 2592000000
    private const val MILLIS_IN_YEAR = 31536000000

    fun getTicketTimeMillis(): Long = DateTime.now().minus(TIME_START.millis).millis

    fun getDate(date: String): String {
        val dateTime = DateTime.parse(date)
        return "${dateTime.year}-${dateTime.monthOfYear}-${dateTime.dayOfMonth}"
    }

    fun getTime(date: String): String {
        val dateTime = DateTime.parse(date)
        return "${dateTime.hourOfDay}".plus(":").plus(dateTime.minuteOfHour).plus(":").plus(dateTime.secondOfMinute)
    }

    fun calculateWorkHours(shiftReportResponse: ShiftReportResponse): String {
        val startTime = SimpleDateFormat(START_SHIFT_DATE_FORMAT).parse(shiftReportResponse.startTime)
        val endTime = SimpleDateFormat(END_SHIFT_DATE_FORMAT).parse(shiftReportResponse.endTime)

        val millis = endTime.time - startTime.time
        val workTime = calculateWorkTime(millis)
        return " ${workTime.hours} "
            .plus("H")
            .plus(" ${workTime.minutes} ")
            .plus("M")
    }
    fun calculateWorkHours(shiftReportResponse: LongManifestoEndShiftResponse): String {
        val startTime = SimpleDateFormat(START_SHIFT_DATE_FORMAT).parse(shiftReportResponse.shiftStartTime)
        val endTime = SimpleDateFormat(END_SHIFT_DATE_FORMAT).parse(shiftReportResponse.shiftEndTime)

        val millis = endTime.time - startTime.time
        val workTime = calculateWorkTime(millis)
        return " ${workTime.hours} "
            .plus("H")
            .plus(" ${workTime.minutes} ")
            .plus("M")
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
     fun calculateWorkHoursAndMinutes(shiftReportResponse: LongManifestoEndShiftResponse) :String{
        val totalSecondMilliSeconds = shiftReportResponse.workSeconds
        val seconds = totalSecondMilliSeconds/1000
        val hours = seconds /3600
        val minutes = (seconds%3600)/60
        return getWorkTimeString(hours , minutes)
    }
    private fun getWorkTimeString(hours:Long , minutes:Long):String{
        return " $hours "
            .plus("H")
            .plus(" $minutes ")
            .plus("M")
    }
}