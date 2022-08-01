package com.comcept.rmrscustomer.data_class.profile.profileresponse

data class Result(
    val CustomerID:String,
    val Name:String?= null,
    val Email:String?= null,
    val MobileNumber:String?= null
)
