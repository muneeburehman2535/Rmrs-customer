package com.teletaleem.rmrs_customer.data_class.checkout

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Checkout(
        val RestaurantID:String,
        val RestaurantName:String,
        val CustomerRegistered:Boolean,
        val CustomerID:String,
        val CustomerName:String,
        val TotalAmount:Int,
        val SalesTax:Int,
        val Status:String,
        val MenuOrdered:ArrayList<MenuOrdered>,
        val Delivery:Delivery


)
