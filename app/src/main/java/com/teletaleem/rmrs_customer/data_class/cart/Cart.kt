package com.teletaleem.rmrs_customer.data_class.cart

data class Cart(
    val item_name:String,
    val item_desc:String,
    val item_price:String,
    val discount:String,
    var quantity:String
)
