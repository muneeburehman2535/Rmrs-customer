package com.comcept.rmrscustomer.data_class.checkout.checkout_response

import com.comcept.rmrscustomer.data_class.checkout.MenuOrdered

data class Data(
        val MenuOrdered:ArrayList<MenuOrdered>,
        val _id:String,
        val OrderID:String,
        val RestaurantID:String,
        val RestaurantName:String,
        val CustomerRegistered:Boolean,
        val CustomerID:String,
        val TotalAmount:Float,
        val SalesTax:Float,
        val Status:String,
        val OrderDate:String,
        val __v:Int,
        val description:String,
        val OwnerID:String,
        val SubTotal:Float,
        val DeliveryCharges:Float,
        var Comments:String,
        var PaymentMethod:String,
        val CustomerAddress:String,
        val SalesTaxRate:Float,
)
