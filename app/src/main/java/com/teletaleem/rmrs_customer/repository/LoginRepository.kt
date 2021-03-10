package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTPResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class LoginRepository {

    private lateinit var loginResponseLiveData: MutableLiveData<LoginResponse>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getLoginResponseLiveData(login: Login):LiveData<LoginResponse> {
        loginResponseLiveData=MutableLiveData<LoginResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.login(login)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var loginResponse: LoginResponse?=null

                loginResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), LoginResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), LoginResponse::class.java)

                }
                Timber.d(Gson().toJson(loginResponse).toString())
                loginResponseLiveData.postValue(loginResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               Timber.e("Error: ${t.message.toString()}")
            }
        })
        return loginResponseLiveData
    }

}