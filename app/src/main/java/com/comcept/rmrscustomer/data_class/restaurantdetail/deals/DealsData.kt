package com.comcept.rmrscustomer.data_class.restaurantdetail.deals

import com.comcept.rmrscustomer.data_class.restaurantdetail.Variant

data class DealsData(
    val _id:String,
    val Variant:ArrayList<Variant>,
    val DealID:String,
    val DealName:String,
    val Description:String,
    val RestaurantID:String,
    val Image:String,
    val DealPrice:Float
)
