package com.teletaleem.rmrs_customer.db.repository

import com.teletaleem.rmrs_customer.data_class.cart.Cart
import com.teletaleem.rmrs_customer.db.dao.CartDao
import com.teletaleem.rmrs_customer.db.dao.FavouriteDao
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.withContext

class CartDBRepository @Inject constructor(private val cartDao: CartDao) {

    /********************************************Cart Methods*****************************************/

    suspend fun insertItem(cart: Cart)=withContext(Dispatchers.IO) {cartDao.insert(cart)}

    suspend fun updateItem(cart: Cart)=cartDao.updateItemRecord(cart)
    suspend fun deleteRecord(cart: Cart)=cartDao.deleteItem(cart)
    fun emptyCart()=cartDao.emptyCart()
}