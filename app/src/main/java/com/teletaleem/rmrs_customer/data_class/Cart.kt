package com.teletaleem.rmrs_customer.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey()
    val restaurant_id:String,
    val item_name:String,
    val item_desc:String,
    val item_price:String,
    val original_price:String,
    val quantity:String,
    val image:String
)
