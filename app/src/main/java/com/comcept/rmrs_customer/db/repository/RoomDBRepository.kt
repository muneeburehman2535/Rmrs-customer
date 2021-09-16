package com.comcept.rmrs_customer.db.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.comcept.rmrs_customer.db.dao.CartDao
import com.comcept.rmrs_customer.data_class.cart.Cart
import com.comcept.rmrs_customer.data_class.cart.SingleCart
import com.comcept.rmrs_customer.data_class.login.LoginResponse
import com.comcept.rmrs_customer.db.dao.FavouriteDao
import com.comcept.rmrs_customer.db.dataclass.Favourite
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

    suspend fun insertItem(cart: Cart):Long=withContext(Dispatchers.IO) {cartDao.insert(cart)}

    suspend fun updateItem(cart: Cart)= withContext(Dispatchers.IO){cartDao.updateItemRecord(cart)}
    suspend fun deleteRecord(cart: Cart)= withContext(Dispatchers.IO){cartDao.deleteItem(cart)}
    suspend fun emptyCart()= withContext(Dispatchers.IO){cartDao.emptyCart()}


    /*******************************************Favourite Methods**************************************/

    suspend fun insertFavouriteItem(favourite: Favourite) = withContext(Dispatchers.IO) {favouriteDao.insert(favourite)}

//    var searchRestaurantById:Flow<Favourite> =favouriteDao.findRestaurantByID(restaurantID)


    suspend fun deleteFavouriteItem(favourite: Favourite)=favouriteDao.deleteFavouriteItem(favourite)



}
