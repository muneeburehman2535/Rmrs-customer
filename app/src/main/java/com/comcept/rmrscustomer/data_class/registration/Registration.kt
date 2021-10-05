package com.comcept.rmrscustomer.data_class.registration

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Registration(
    val Name:String,
    val Email:String,
    val CNIC:String,
    val MobileNumber:String,
    val PhoneNumber:String,
    val Password:String,
    val Address:String
):Serializable
