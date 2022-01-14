package com.comcept.rmrscustomer.data_class.restaurantdetail.profile

import com.comcept.rmrscustomer.data_class.restaurantdetail.Delivery


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
        val Rating:Double,
        val RatingCount:Int,
        val SumofRating:Double,
        val Image:String,
        val __v:Int,
        val SalesTax:Float,
        val ServiceCharges:Float,
        val City:String,
        val Delivery:ArrayList<Delivery>
)
