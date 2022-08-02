package com.englizya.common.utils.navigation

import android.net.Uri

object NavigationUtils {

    fun getUriNavigation(domain: String, destination: String, argument: String? = null): Uri {
        return "$domain://$destination/$argument".let { Uri.parse(it) }
    }

    fun getUriNavigation(
        domain: String,
        destination: String,
        containsArgs: Boolean = false,
        argument: String? = null
    ): Uri {
        return if (containsArgs) {
            "$domain://$destination/${argument}"
        } else {
            "$domain://$destination"
        }.let {
            Uri.parse(it)
        }
    }
    fun getUriNavigation(
        domain: String,
        destination: String,
        containsArgs: Boolean = false,
        argument1: String? = null,
        argument2 : String? = null
    ): Uri {
        return if (containsArgs) {
            "$domain://$destination/${argument1}/${argument2}"
        } else {
            "$domain://$destination"
        }.let {
            Uri.parse(it)
        }
    }
}