package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.teletaleem.rmrs_customer.conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.data_class.profile.Profile
import com.teletaleem.rmrs_customer.data_class.profile.profileresponse.ProfileResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ProfileRepository {

    private lateinit var profileResponseLiveData: MutableLiveData<ProfileResponse>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getProfileResponseLiveData(profile: Profile): LiveData<ProfileResponse> {
        profileResponseLiveData= MutableLiveData<ProfileResponse>()
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
                profileResponseLiveData.postValue(profileResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return profileResponseLiveData
    }
}