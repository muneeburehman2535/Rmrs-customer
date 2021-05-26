package com.teletaleem.rmrs_customer.data_class.checkout

import com.squareup.moshi.JsonClass
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Variant

@JsonClass(generateAdapter = true)
data class MenuOrdered(
        val MenuID:String,
        val MenuName:String,
        val MenuPrice:String,
        val Quantity:String,
        val Description:String,
        val variant:Variant
)
