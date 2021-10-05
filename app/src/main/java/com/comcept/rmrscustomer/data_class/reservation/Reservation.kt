package com.comcept.rmrscustomer.data_class.reservation

import java.util.*

data class Reservation(
        val RestaurantID:String,
        val CustomerID:String,
        val CustomerName:String,
        val NumberOfPeople:String,
        val MobileNumber:String,
        val ReservationTime:Date,
        val RestaurantName:String
)
