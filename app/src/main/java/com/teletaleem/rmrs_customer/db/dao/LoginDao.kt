package com.teletaleem.rmrs_customer.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse

@Dao
interface LoginDao {

    //Insert New Entries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loginResponse: LoginResponse):Long

    //Fetch Records
    @Query("select * from login")
    fun fetchRecords():LiveData<MutableList<LoginResponse>>

    //Delete records from table
    @Query("DELETE FROM login")
    suspend fun deleteRecord()
}