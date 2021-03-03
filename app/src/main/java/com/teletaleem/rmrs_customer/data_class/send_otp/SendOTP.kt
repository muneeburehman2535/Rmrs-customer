package com.teletaleem.rmrs_customer.data_class.send_otp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendOTP(
    val Email:String,
    val MobileNumber:String
)
