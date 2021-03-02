package com.teletaleem.rmrs_customer.data_class.login

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Data(
    @PrimaryKey(autoGenerate = true)
    val id:String,
    @ColumnInfo(name="Token")
    val Token:String,
    @ColumnInfo(name="CustomerID")
    val CustomerID:String,
    @ColumnInfo(name="Email")
    val Email:String,
    @ColumnInfo(name="Name")
    val Name:String,
    @ColumnInfo(name="MobileNumber")
    val MobileNumber:String
)
