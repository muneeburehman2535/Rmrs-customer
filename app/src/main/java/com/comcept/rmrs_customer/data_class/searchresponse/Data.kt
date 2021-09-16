package com.comcept.rmrs_customer.data_class.searchresponse

import com.comcept.rmrs_customer.data_class.home.restaurants.Restaurants

data class Data(
        val result:ArrayList<Restaurants>,
        val description:String
)
