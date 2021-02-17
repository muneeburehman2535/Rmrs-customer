package com.teletaleem.rmrs_customer.db.data_class

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class Login(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0L,
    @ColumnInfo(name = "email")
    val email:String,
    @ColumnInfo(name = "password")
    val password:String
    )
