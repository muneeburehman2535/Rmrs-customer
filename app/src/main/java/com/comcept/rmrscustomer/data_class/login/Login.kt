package com.comcept.rmrscustomer.data_class.login

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Login(
    val Email:String,
    val Password:String
)
