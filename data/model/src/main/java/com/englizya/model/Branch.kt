package com.englizya.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Branch(
    @SerializedName("branchId") var branchId: Int? = null,
    @SerializedName("branchName") var branchName: String? = null,
    @SerializedName("areaId") var areaId: Int? = null,
    @SerializedName("bookingOfficeList") var bookingOfficeList: ArrayList<BookingOfficeList> = arrayListOf()
)