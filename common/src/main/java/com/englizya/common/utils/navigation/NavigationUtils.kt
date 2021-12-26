package com.englizya.common.utils.navigation

import android.net.Uri

object NavigationUtils {

    fun getUriNavigation(domain: String, destination: String, argument: String? = null): Uri {
        return "$domain://$destination/$argument".let { Uri.parse(it) }
    }
}