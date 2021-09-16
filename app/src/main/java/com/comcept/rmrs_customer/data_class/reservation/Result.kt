package com.comcept.rmrs_customer.data_class.reservation

data class Result(
    val _id:String,
    val ReservationID:String,
    val RestaurantID:String,
    val CustomerID:String,
    val CustomerName:String,
    val NumberOfPeople:String,
    val ReservationTime:String,
    val RestaurantName:String,
    val MobileNumber:String,
    val Status:String,
    val __v:Int
)
