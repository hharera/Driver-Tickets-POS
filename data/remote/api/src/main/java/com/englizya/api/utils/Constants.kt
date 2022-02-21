package com.englizya.api.utils

object Routing {
    const val END_SHIFT = "${Domain.ENGLIZYA_PAY}end-shift"
    const val LOGIN = "${Domain.ENGLIZYA_PAY}login"
    const val GET_MANIFESTO = "${Domain.ENGLIZYA_PAY}manifesto"
    const val TICKET = "${Domain.ENGLIZYA_PAY}ticket"
    const val TICKETS = "${Domain.ENGLIZYA_PAY}tickets"
}

object Request {
    const val TIME_OUT = 5000.toLong()
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
    const val ENGLIZYA_PAY = "http://161.97.71.140:9999/"
}