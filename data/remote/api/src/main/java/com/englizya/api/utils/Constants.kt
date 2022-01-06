package com.englizya.api.utils

object Routing {
    const val INSERT_MESSAGE = "${Domain.ENGLIZYA_PAY}/message"
    const val GET_PROFILE_POSTS = "${Domain.ENGLIZYA_PAY}/profile/posts"
    const val GET_CONNECTIONS = "${Domain.ENGLIZYA_PAY}/connections"
    const val LOGIN = "${Domain.ENGLIZYA_PAY}/login"
}

object Parameters {
    const val ACCESS_TOKEN: String = "access_token"
    const val PID = "pid"
}

object LoginMethods {
    const val EMAIL = "email"
    const val FACEBOOK = "facebook"
    const val Google = "google"
}

object AuthenticationParameters {
    const val USERNAME = "username"
    const val BEARER = "Bearer"
}

object Constants {
    const val TOKEN = "token"
}

object Domain {
    const val ENGLIZYA_PAY = "http://161.97.71.140:8081/"
}