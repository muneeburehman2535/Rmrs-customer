package com.comcept.rmrscustomer.data_class.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "single_cart")
data class SingleCart(
    @ColumnInfo(name = "restaurant_id")
    var restaurant_id:String?=null,
    @ColumnInfo(name = "restaurant_name")
    val restaurant_name:String?=null,
    @ColumnInfo(name = "item_name")
    val item_name:String?=null,
    @PrimaryKey()
    @ColumnInfo(name = "menu_id")
    val menu_id:String?=null,
    @ColumnInfo(name = "item_desc")
    val item_desc:String?=null,
    @ColumnInfo(name = "item_price")
    val item_price:String?=null,
    @ColumnInfo(name = "original_price")
    val original_price:String?=null,
    @ColumnInfo(name = "quantity")
    var quantity:String?=null,
    @ColumnInfo(name="image")
    val image:String?=null,
    @ColumnInfo(name = "description")
    val description:String?=null,
    @ColumnInfo(name = "restaurant_address")
    val restaurant_address:String?=null,
    @ColumnInfo(name = "restaurant_image")
    val restaurant_image:String?=null,
    @ColumnInfo(name = "variant")
    val variant:String?=null,
    @ColumnInfo(name = "is_variant")
    val is_variant:String?=null,
    @ColumnInfo(name = "variant_id")
    val variant_id:String?=null
)
