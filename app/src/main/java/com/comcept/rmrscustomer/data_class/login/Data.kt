package com.comcept.rmrscustomer.data_class.login

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Data(
    val Token:String,
    val CustomerID:String,
    val Email:String,
    val Name:String,
    val MobileNumber:String,
    val description:String
)
