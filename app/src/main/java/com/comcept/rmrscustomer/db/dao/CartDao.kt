package com.comcept.rmrscustomer.db.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.data_class.cart.SingleCart

@Dao
interface CartDao {

    //Insert new record in cart
    @WorkerThread
    @Insert
    suspend fun insert(cart: Cart):Long

    //Get all cart records
    @Query("select * from cart where restaurant_id = :restaurantId")
    fun fetch(restaurantId:String):LiveData<MutableList<Cart>>

    @Query("select * from cart")
    fun fetchAllRecord():LiveData<MutableList<Cart>>


    @Query ("select * from cart where restaurant_id = :restaurantId and menu_id = :menuId and variant_id = :variantId")
    fun fetchCartRecord(restaurantId: String, menuId:String, variantId:String):LiveData<Cart>


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItemRecord(cart: Cart)

    //Delete selected Item in cart
    @Delete
    suspend fun deleteItem(cart: Cart)

    //Empty Cart
    @Query("DELETE from cart")
    suspend fun emptyCart()

}