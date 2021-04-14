package com.teletaleem.rmrs_customer.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.db.dao.CartDao
import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.db.dao.FavouriteDao
import com.teletaleem.rmrs_customer.db.dataclass.Favourite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.Flow
import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val favouriteDao: FavouriteDao,private val cartDao: CartDao) {

//    /*********************************************Login Methods***************************************/
//    suspend fun insertLoginData(loginResponse: LoginResponse)=loginDao.insert(loginResponse)
//    fun fetchLoginCredentials()=loginDao.fetchRecords()


    /********************************************Cart Methods*****************************************/

    suspend fun insertItem(cart: Cart)=withContext(Dispatchers.IO) {cartDao.insert(cart)}

    suspend fun updateItem(cart: Cart)=cartDao.updateItemRecord(cart)
    suspend fun deleteRecord(cart: Cart)=cartDao.deleteItem(cart)
    fun emptyCart()=cartDao.emptyCart()

    /*******************************************Favourite Methods**************************************/

    suspend fun insertFavouriteItem(favourite: Favourite) = withContext(Dispatchers.IO) {favouriteDao.insert(favourite)}

//    var searchRestaurantById:Flow<Favourite> =favouriteDao.findRestaurantByID(restaurantID)


    suspend fun deleteFavouriteItem(favourite: Favourite)=favouriteDao.deleteFavouriteItem(favourite)



}
