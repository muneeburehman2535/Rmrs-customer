package com.teletaleem.rmrs_customer.repository

import com.teletaleem.rmrs_customer.db.dao.CartDao
import com.teletaleem.rmrs_customer.db.dao.LoginDao
import com.teletaleem.rmrs_customer.data_class.Cart
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import javax.inject.Inject

class RoomDBRepository @Inject constructor(private val loginDao: LoginDao,private val cartDao: CartDao) {

    /*********************************************Login Methods***************************************/
    suspend fun insertLoginData(loginResponse: LoginResponse)=loginDao.insert(loginResponse)
    fun fetchLoginCredentials()=loginDao.fetchRecords()


    /********************************************Cart Methods*****************************************/

    suspend fun insertItem(cart: Cart)=cartDao.insert(cart)
    fun fetchRecords()=cartDao.fetch()
    suspend fun updateItem(cart: Cart)=cartDao.updateItemRecord(cart)
    suspend fun deleteRecord(cart: Cart)=cartDao.deleteItem(cart)
    fun emptyCart()=cartDao.emptyCart()


}