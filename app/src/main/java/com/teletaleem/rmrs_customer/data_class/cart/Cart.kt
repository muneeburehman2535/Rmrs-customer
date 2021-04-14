package com.teletaleem.rmrs_customer.data_class.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
        @PrimaryKey()
        @ColumnInfo(name = "restaurant_id")
        var restaurant_id:String,
        @ColumnInfo(name = "item_name")
        val item_name:String,
        @ColumnInfo(name = "item_desc")
        val item_desc:String,
        @ColumnInfo(name = "item_price")
        val item_price:String,
        @ColumnInfo(name = "original_price")
        val original_price:String,
        @ColumnInfo(name = "quantity")
        var quantity:String,
        @ColumnInfo(name="image")
        val image:String
)
