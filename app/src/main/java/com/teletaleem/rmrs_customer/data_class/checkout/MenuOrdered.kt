package com.teletaleem.rmrs_customer.data_class.checkout

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MenuOrdered(
        val MenuID:String,
        val MenuName:String,
        val MenuPrice:String,
        val Quantity:String,
        val Description:String
)
