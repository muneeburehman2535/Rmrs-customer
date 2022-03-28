package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.login.Login
import com.comcept.rmrscustomer.data_class.login.LoginResponse
import com.comcept.rmrscustomer.data_class.profile.Profile
import com.comcept.rmrscustomer.data_class.profile.profileresponse.ProfileResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ProfileRepository {

    private lateinit var profileResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<ProfileResponse>>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getProfileResponseLiveData(profile: Profile): LiveData<com.comcept.rmrscustomer.repository.Response<ProfileResponse>> {
        profileResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<ProfileResponse>>()

        profileResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.updateCustomerProfile(profile)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var profileResponse: ProfileResponse?=null

                profileResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ProfileResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ProfileResponse::class.java)

                }
                Timber.d(Gson().toJson(profileResponse).toString())
                profileResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(profileResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                profileResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return profileResponseLiveData
    }
}