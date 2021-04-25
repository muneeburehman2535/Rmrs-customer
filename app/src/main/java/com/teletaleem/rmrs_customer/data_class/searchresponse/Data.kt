package com.teletaleem.rmrs_customer.data_class.searchresponse

import com.teletaleem.rmrs_customer.data_class.home.restaurants.Restaurants

data class Data(
        val result:ArrayList<Restaurants>,
        val description:String
)
