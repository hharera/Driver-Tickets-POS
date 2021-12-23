package com.englizya.ticket

sealed class PaymentMethod(val icon: Int, val title: Int) {
    object Cash : PaymentMethod(icon = R.drawable.cash_100, R.string.cash)
    object Card : PaymentMethod(icon = R.drawable.card_100, R.string.card)
    object QR : PaymentMethod(icon = R.drawable.qr_100, R.string.scan_qr)
}