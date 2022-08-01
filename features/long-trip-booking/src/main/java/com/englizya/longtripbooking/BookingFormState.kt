package com.englizya.longtripbooking

data class BookingFormState(
    var sourceError: Int? = null,
    var destinationError: Int? = null,
    var formIsValid: Boolean = false,
)
