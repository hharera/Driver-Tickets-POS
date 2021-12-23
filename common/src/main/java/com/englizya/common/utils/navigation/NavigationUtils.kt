package com.englizya.common.utils.navigation

object NavigationUtils {

    fun getUriNavigation(domain: String, destination: String, argument: String?): String {
        return "$domain://$destination/$argument"
    }
}