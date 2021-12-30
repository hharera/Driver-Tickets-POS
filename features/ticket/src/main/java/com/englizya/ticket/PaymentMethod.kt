package com.englizya.ticket

sealed class PaymentMethod(val iconRes: Int, val titleRes: Int) {
    object Cash : PaymentMethod(iconRes = R.drawable.cash_100, R.string.cash)
    object Card : PaymentMethod(iconRes = R.drawable.card_100, R.string.card)
    object QR : PaymentMethod(iconRes = R.drawable.qr_100, R.string.scan_qr)
}