package com.comcept.rmrscustomer.data_class.restaurantdetail

data class Menu(
        val _Id:String,
        val MenuID:String,
        val MenuName:String,
        val CalculatedPrice:Float,
        val MenuCategory:String,
        val Discount:Float,
        val RestaurantID:String,
        val MenuAvailable:Boolean,
        val CategoryID:String,
        val Description:String,
        val ItemPrice:Float,
        val Image:String,
        val __v:Int,
        val Variant:ArrayList<Variant>,
        val isVariant:Boolean
)
