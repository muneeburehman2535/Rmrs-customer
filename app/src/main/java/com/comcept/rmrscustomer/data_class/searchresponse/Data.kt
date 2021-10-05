package com.comcept.rmrscustomer.data_class.searchresponse

import com.comcept.rmrscustomer.data_class.home.restaurants.Restaurants

data class Data(
        val result:ArrayList<Restaurants>,
        val description:String
)
