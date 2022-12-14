package com.comcept.rmrscustomer.db.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey()
    @ColumnInfo(name = "restaurant_id")
    var restaurant_id:String,
    @ColumnInfo(name = "restaurant_name")
    var restaurant_name:String,
    @ColumnInfo(name = "address")
    var address:String,
    @ColumnInfo(name = "rating")
    var rating:String,
    @ColumnInfo(name = "rating_count")
    var rating_count:String,
    @ColumnInfo(name = "image")
    var image:String
)
