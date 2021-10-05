package com.comcept.rmrscustomer.data_class.email_mobile

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier

@JsonClass(generateAdapter = true)
data class Data(
        @ColumnInfo(name = "description")
        val description:String,
        @ColumnInfo(name = "Email")
        val Email:Boolean,
        @ColumnInfo(name = "MobileNumber")
        val MobileNumber:Boolean,
)
