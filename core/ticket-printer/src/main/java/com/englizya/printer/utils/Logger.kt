package com.englizya.printer.utils

import android.util.Log

open class Logger {
    private val TAG = "Logger"

    fun logTrue(method: String) {
        Log.d(TAG, "logTrue: $method")
    }

    fun logErr(method: String, errString: String) {
        val errorLog = "$method   errorMessage：$errString"
        Log.e(TAG, "logErr: $errorLog")
    }
}