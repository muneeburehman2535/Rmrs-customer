package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {

    private lateinit var loginResponseLiveData: MutableLiveData<LoginResponse>
    private val retrofitClass:RetrofitClass= RetrofitClass().getHomeInstance()!!

    fun getLoginResponseLiveData(login: Login):LiveData<LoginResponse> {
        retrofitClass.getHomeRequestsInstance()?.login(login)?.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                loginResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                //loginResponseLiveData.postValue()
            }
        })
        return loginResponseLiveData
    }

}