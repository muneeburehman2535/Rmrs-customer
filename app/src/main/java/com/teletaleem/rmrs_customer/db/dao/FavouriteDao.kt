package com.teletaleem.rmrs_customer.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.db.dataclass.Favourite

@Dao
interface FavouriteDao {

    //Insert new record in cart
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: Favourite):Long

    @Query("select * from favourite where restaurant_id = :restaurantId  LIMIT 1")
    fun findRestaurantByID(restaurantId:String): Favourite?
}