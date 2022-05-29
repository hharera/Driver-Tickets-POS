package com.englizya.api.utils

object Routing {
    const val REQUEST_TICKETS_WITH_WALLET = "${Domain.ENGLIZYA_BUS}/api/driver/request-short-tickets-with-wallet"
    const val GET_TRIP = "${Domain.ENGLIZYA_BUS}/api/trip"
    const val GET_WALLET_DETAILS = "${Domain.ENGLIZYA_BUS}/api/driver/get-wallet-details"
    const val END_SHIFT = "${Domain.ENGLIZYA_STAFF}/end-shift"
    const val LOGIN = "${Domain.ENGLIZYA_STAFF}/login"
    const val GET_MANIFESTO = "${Domain.ENGLIZYA_STAFF}/manifesto"
    const val TICKET = "${Domain.ENGLIZYA_STAFF}/ticket"
    const val TICKETS = "${Domain.ENGLIZYA_STAFF}/tickets"
}

object Request {
    const val TIME_OUT = 5000.toLong()
}

object Parameters {
    const val CATEGORY = "category"
    const val QUANTITY = "quantity"
    const val UID = "uid"
}

object AuthenticationParameters {
    const val BEARER = "Bearer"
}

object Domain {
    const val ENGLIZYA_STAFF = "http://161.97.71.140:9105"
//    const val ENGLIZYA_STAFF = "http://192.168.1.190:9105"

    const val ENGLIZYA_BUS = "http://161.97.71.140:9100"
//    const val ENGLIZYA_BUS = "http://192.168.1.190:9100"
}