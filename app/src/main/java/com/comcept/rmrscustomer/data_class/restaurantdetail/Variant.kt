package com.comcept.rmrscustomer.data_class.restaurantdetail

data class Variant(
    var ItemName:String,
    var ItemPrice:Float,
    var CalculatedPrice:Float,
    var DiscountPrice:Int,
    var isChecked:Boolean,
    var Quantity:Int,
    var VariantID:String
)
