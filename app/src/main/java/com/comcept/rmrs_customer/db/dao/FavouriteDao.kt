package com.comcept.rmrs_customer.db.dao

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.comcept.rmrs_customer.data_class.cart.Cart
import com.comcept.rmrs_customer.db.dataclass.Favourite

@Dao
interface FavouriteDao {

    //Insert new record in favourites
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(favourite: Favourite):Long

    @Query("select * from favourite")
    fun findRestaurants(): LiveData<MutableList<Favourite >>

    //Delete selected Item in Favourite
    @Delete
    suspend fun deleteFavouriteItem(favourite: Favourite)


    @Query("select * from favourite where restaurant_id = :restaurantId")
    fun fetchFavouriteRecord(restaurantId:String):LiveData<MutableList<Favourite>>
}