package com.teletaleem.rmrs_customer.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.teletaleem.rmrs_customer.data_class.Cart

@Dao
interface CartDao {

    //Insert new record in cart
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: Cart):Long

    //Get all cart records
    @Query("select * from cart")
    fun fetch():LiveData<MutableList<Cart>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItemRecord(cart: Cart)

    //Delete selected Item in cart
    @Delete
    suspend fun deleteItem(cart: Cart)

    //Empty Cart
    @Query("DELETE from cart")
    fun emptyCart()
}