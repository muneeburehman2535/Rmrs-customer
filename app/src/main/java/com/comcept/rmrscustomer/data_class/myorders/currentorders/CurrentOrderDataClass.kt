package com.comcept.rmrscustomer.data_class.myorders.currentorders

import com.comcept.rmrscustomer.data_class.checkout.MenuOrdered

data class CurrentOrderDataClass(
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
    val __v:Int
)
