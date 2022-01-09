package com.englizya.printer.utils

import org.joda.time.DateTime

object Time {

    val TIME_START = DateTime(2022, 1, 1, 0, 0)

    fun getTicketTimeMillis(): Long = DateTime.now().minus(TIME_START.millis).millis
}