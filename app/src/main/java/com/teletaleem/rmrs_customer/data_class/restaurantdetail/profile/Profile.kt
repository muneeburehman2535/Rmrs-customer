package com.teletaleem.rmrs_customer.data_class.restaurantdetail.profile

data class Profile(
        val Timings:ArrayList<Timings>,
        val Devices:ArrayList<Devices>,
        val RestaurantID:String,
        val RestaurantName:String,
        val RestaurantCategory:String,
        val CategoryID:String,
        val Address:String,
        val NTNNumber:String,
        val RestaurantVerified:Boolean,
        val TotalDevices:Int,
        val OwnerID:String,
        val Rating:Int,
        val RatingCount:Int,
        val Image:String,
        val __v:Int
)
