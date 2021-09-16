package com.comcept.rmrs_customer.data_class.restaurantdetail

import com.comcept.rmrs_customer.data_class.restaurantdetail.profile.Profile

data class Data(
        val profile:ArrayList<Profile>,
        val menu:ArrayList<Menu>,
        val description:String
)
