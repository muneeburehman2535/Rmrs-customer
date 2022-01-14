package com.comcept.rmrscustomer.data_class.registration

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import com.comcept.rmrscustomer.data_class.login.Data

@JsonClass(generateAdapter = true)
data class RegistrationResponse (
    @ColumnInfo(name ="Message" )
    val message:String,
    @ColumnInfo(name="data")
    val data:Data
    )