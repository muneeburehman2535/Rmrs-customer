package com.teletaleem.rmrs_customer.data_class.email_mobile

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier

@JsonClass(generateAdapter = true)
data class Data(
    @ColumnInfo(name = "Email")
    val email:Boolean,
    @ColumnInfo(name = "PhoneNumber")
    val phoneNumber:Boolean
)
