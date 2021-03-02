package com.teletaleem.rmrs_customer.data_class.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(

    @ColumnInfo(name ="Message" )
    val message:String,
    @ColumnInfo(name="data")
    val data:Data
    )
