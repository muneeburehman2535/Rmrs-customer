package com.comcept.rmrs_customer.data_class.myorders

import com.comcept.rmrs_customer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.comcept.rmrs_customer.data_class.myorders.pastorders.PastOrdersDataClass

data class Data(
    val CurrentOrder:ArrayList<CurrentOrderDataClass>,
    val PastOrders:ArrayList<PastOrdersDataClass>,
    val description:String
)
