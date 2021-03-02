package com.teletaleem.rmrs_customer.data_class.email_mobile

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailMobileVerification(
    @ColumnInfo(name = "Email")
    val email:String,
    @ColumnInfo(name = "PhoneNumber")
    val mobileNumber:String
)
