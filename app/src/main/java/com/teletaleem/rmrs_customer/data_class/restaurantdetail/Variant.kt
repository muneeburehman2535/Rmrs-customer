package com.teletaleem.rmrs_customer.data_class.restaurantdetail

data class Variant(
    val ItemName:String,
    val ItemPrice:Float,
    val CalculatedPrice:String,
    val DiscountPrice:Int,
    var isChecked:Boolean
)
