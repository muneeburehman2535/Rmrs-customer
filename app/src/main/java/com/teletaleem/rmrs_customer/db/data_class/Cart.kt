package com.teletaleem.rmrs_customer.db.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0L,
)
