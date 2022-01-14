package com.comcept.rmrscustomer.data_class.review

data class Review(
        val CustomerName:String,
        val Rating:Double,
        val Comment:String,
        val RestaurantID:String,
        val SumofRating:Double,
        val TotalCount:Int
)
