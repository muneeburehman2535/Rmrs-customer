package com.comcept.rmrscustomer.data_class.restaurantdetail

import com.comcept.rmrscustomer.data_class.restaurantdetail.profile.Profile

data class Data(
        val profile:ArrayList<Profile>,
        val menu:ArrayList<Menu>,
        val description:String
)
