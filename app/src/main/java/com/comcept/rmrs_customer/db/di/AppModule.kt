package com.comcept.rmrs_customer.db.di

import android.content.Context
import com.comcept.rmrs_customer.db.CustomerDatabase
import com.comcept.rmrs_customer.db.dao.CartDao
import com.comcept.rmrs_customer.db.dao.FavouriteDao
import com.comcept.rmrs_customer.db.repository.RoomDBRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule  {

    @Provides
    fun providesUserDao(userDatabase: CustomerDatabase):FavouriteDao = userDatabase.favouriteDao

    @Provides
    fun providesCartDao(userDatabase: CustomerDatabase):CartDao=userDatabase.cartDao

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context):CustomerDatabase
            = CustomerDatabase.getInstance(context)

    @Provides
    fun providesUserRepository(userDao: FavouriteDao,cartDao: CartDao) : RoomDBRepository
            = RoomDBRepository(userDao,cartDao)

//    @Provides
//    fun providesCartRepository(cartDao: CartDao):CartDBRepository= CartDBRepository(cartDao)

}