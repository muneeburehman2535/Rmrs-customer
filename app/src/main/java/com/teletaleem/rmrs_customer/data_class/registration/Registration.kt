package com.teletaleem.rmrs_customer.data_class.registration

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Registration(
    val fullName:String,
    val email:String,
    val cnic:String,
    val mobile:String,
    val phoneNumber:String,
    val password:String,
    val address:String
)
