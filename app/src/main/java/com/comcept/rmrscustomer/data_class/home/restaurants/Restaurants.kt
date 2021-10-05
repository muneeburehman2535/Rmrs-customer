package com.comcept.rmrscustomer.data_class.home.restaurants

data class Restaurants(
        val _id:String,
        val RestaurantID:String,
        val RestaurantName:String,
        val Address:String,
        val Rating:String,
        val RatingCount:String,
        val CategoryID:String,
        val RestaurantCategory:String,
        val Image:String,
        var isFavourite:Boolean
)
