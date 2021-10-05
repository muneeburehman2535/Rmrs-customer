package com.comcept.rmrscustomer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.data_class.cart.SingleCart
import com.comcept.rmrscustomer.db.dao.CartDao
import com.comcept.rmrscustomer.db.dao.FavouriteDao
import com.comcept.rmrscustomer.db.dataclass.Favourite

@Database(entities = [Favourite::class, Cart::class], version = 1,exportSchema = false)
abstract class CustomerDatabase:RoomDatabase(){


    abstract val favouriteDao:FavouriteDao
    abstract val cartDao:CartDao
    companion object {



        @Volatile
        private var INSTANCE: CustomerDatabase? = null

        fun getInstance(context: Context): CustomerDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CustomerDatabase::class.java,
                        "customer-database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}