package com.comcept.rmrs_customer.data_class.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "single_cart")
data class SingleCart(
    @ColumnInfo(name = "restaurant_id")
    var restaurant_id:String,
    @ColumnInfo(name = "restaurant_name")
    val restaurant_name:String,
    @ColumnInfo(name = "item_name")
    val item_name:String,
    @PrimaryKey()
    @ColumnInfo(name = "menu_id")
    val menu_id:String,
    @ColumnInfo(name = "item_desc")
    val item_desc:String,
    @ColumnInfo(name = "item_price")
    val item_price:String,
    @ColumnInfo(name = "original_price")
    val original_price:String,
    @ColumnInfo(name = "quantity")
    var quantity:String,
    @ColumnInfo(name="image")
    val image:String,
    @ColumnInfo(name = "description")
    val description:String,
    @ColumnInfo(name = "restaurant_address")
    val restaurant_address:String,
    @ColumnInfo(name = "restaurant_image")
    val restaurant_image:String,
    @ColumnInfo(name = "variant")
    val variant:String,
    @ColumnInfo(name = "is_variant")
    val is_variant:String,
    @ColumnInfo(name = "variant_id")
    val variant_id:String
)