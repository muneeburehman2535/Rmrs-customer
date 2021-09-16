package com.comcept.rmrs_customer.data_class.checkout

import com.squareup.moshi.JsonClass
import com.comcept.rmrs_customer.data_class.restaurantdetail.Variant

@JsonClass(generateAdapter = true)
data class MenuOrdered(
        val MenuID:String,
        val MenuName:String,
        val MenuPrice:String,
        val Quantity:String,
        val Description:String,
        val Variant:Variant,
        val isVariant:Boolean
)
