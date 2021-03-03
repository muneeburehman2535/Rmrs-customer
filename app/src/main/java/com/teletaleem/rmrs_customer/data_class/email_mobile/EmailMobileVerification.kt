package com.teletaleem.rmrs_customer.data_class.email_mobile

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailMobileVerification(
    @ColumnInfo(name = "Email")
    val Email:String,
    @ColumnInfo(name = "PhoneNumber")
    val MobileNumber:String
)
