package com.teletaleem.rmrs_customer.data_class.restaurantdetail

import com.teletaleem.rmrs_customer.data_class.restaurantdetail.profile.Profile

data class Data(
        val profile:ArrayList<Profile>,
        val menu:ArrayList<Menu>,
        val description:String
)
