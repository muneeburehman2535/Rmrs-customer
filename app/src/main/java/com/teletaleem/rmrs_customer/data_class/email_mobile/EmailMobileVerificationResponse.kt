package com.teletaleem.rmrs_customer.data_class.email_mobile

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailMobileVerificationResponse(
    @ColumnInfo(name = "Message")
    val message:String,
    @ColumnInfo(name = "data")
    val date:Data
)
