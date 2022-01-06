package com.englizya.printer

import android.util.Log

open class Logger {
    private val TAG = "Logger"

    fun logTrue(method: String) {
        val errorLog = "$method"
        Log.d(TAG, "logErr: $errorLog")
    }

    fun logErr(method: String, errString: String) {
        val errorLog = "$method   errorMessage：$errString"
        Log.e(TAG, "logErr: $errorLog")
    }
}