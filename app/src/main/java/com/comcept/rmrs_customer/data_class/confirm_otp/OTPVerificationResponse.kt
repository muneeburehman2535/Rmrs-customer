package com.comcept.rmrs_customer.data_class.confirm_otp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OTPVerificationResponse(
    val Message:String,
    val data:Data
)
