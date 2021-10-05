package com.comcept.rmrscustomer.data_class.confirm_otp

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val description:String,
    val status:Boolean
)
