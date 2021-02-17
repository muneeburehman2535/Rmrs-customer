package com.teletaleem.rmrs_customer.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.teletaleem.rmrs_customer.db.data_class.Login

@Dao
interface LoginDao {

    //Insert New Entries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(login:Login):Long

    //Fetch Records
    @Query("select * from login")
    fun fetchRecords():LiveData<MutableList<Login>>

    //Delete records from table
    @Query("DELETE FROM login")
    suspend fun deleteRecord()
}