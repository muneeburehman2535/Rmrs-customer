package com.comcept.rmrs_customer.data_class.fcm

data class FcmNotification(
    val CustomerID:String,
    val DeviceType:String,
    val Token:String,
    val DeviceID:String
)