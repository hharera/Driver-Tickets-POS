package com.englizya.common.utils.network

import android.content.Context
import android.net.ConnectivityManager

class InternetConnection {

    companion object {
        fun checkConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetwork != null
        }
    }
}