package com.teletaleem.rmrs_customer.data_class.restaurantdetail

data class Variant(
    var ItemName:String,
    var ItemPrice:Float,
    var CalculatedPrice:Float,
    var DiscountPrice:Int,
    var isChecked:Boolean,
    var Quantity:Int,
    val VariantID:String
)
