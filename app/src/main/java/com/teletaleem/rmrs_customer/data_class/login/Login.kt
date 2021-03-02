package com.teletaleem.rmrs_customer.data_class.login

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Login(
    val email:String,
    val password:String
)
