package com.teletaleem.rmrs_customer.data_class.restaurantdetail

data class Menu(
        val _Id:String,
        val MenuID:String,
        val MenuName:String,
        val MenuPrice:String,
        val MenuCategory:String,
        val MenuPromo:Int,
        val RestaurantID:String,
        val MenuAvailable:Boolean,
        val CategoryID:String,
        val Description:String,
        val OriginalPrice:String,
        val Image:String,
        val __v:Int
)
