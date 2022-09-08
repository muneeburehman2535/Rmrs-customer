package com.comcept.rmrscustomer.data_class.verifyInvoice

import java.io.Serializable

data class RestaurantList(

    val RestaurantID:String,
    val RestaurantName:String,
    val Region:String,
    val City:String,
    val Division:String,
    val Address:String

):Serializable
