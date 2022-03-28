package com.comcept.rmrscustomer.data_class.checkout

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  Checkout(
        val RestaurantID:String,
        val RestaurantName:String,
        val CustomerRegistered:Boolean,
        val CustomerID:String,
        val CustomerName:String,
        val TotalAmount:Float,
        val SalesTax:Float,
        val Status:String,
        val MenuOrdered:ArrayList<MenuOrdered>,
        val Delivery:Delivery,
        val OwnerID:String,
        val SubTotal:Float,
        val DeliveryCharges:Float,
        var Comments:String,
        var PaymentMethod:String,
        var CustomerAddress:String,
        var OrderType:String,
        val MobileNumber:String

)
