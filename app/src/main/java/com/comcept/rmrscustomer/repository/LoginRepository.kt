package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.login.Login
import com.comcept.rmrscustomer.data_class.login.LoginResponse
import com.comcept.rmrscustomer.data_class.send_otp.SendOTPResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class LoginRepository {

    private lateinit var loginResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<LoginResponse>>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getLoginResponseLiveData(login: Login):LiveData<com.comcept.rmrscustomer.repository.Response<LoginResponse>> {
        loginResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<LoginResponse>>()

        loginResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.login(login)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var loginResponse: LoginResponse?=null

                loginResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), LoginResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), LoginResponse::class.java)

                }
                Timber.d(Gson().toJson(loginResponse).toString())
                loginResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(loginResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               Timber.e("Error: ${t.message.toString()}")

                loginResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return loginResponseLiveData
    }

}