package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePassword
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePasswordResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class UpdatePasswordRepository {

    private lateinit var updatePasswordResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<UpdatePasswordResponse>>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getUpdatePasswordResponseLiveData(updatePassword: UpdatePassword): LiveData<com.comcept.rmrscustomer.repository.Response<UpdatePasswordResponse>> {
        updatePasswordResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<UpdatePasswordResponse>>()

        updatePasswordResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.updateCustomerPassword(updatePassword)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var updatePasswordResponse: UpdatePasswordResponse?=null

                updatePasswordResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), UpdatePasswordResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), UpdatePasswordResponse::class.java)

                }
                Timber.d(Gson().toJson("Update Password: @{updatePasswordResponse)}"))
                updatePasswordResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(updatePasswordResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                updatePasswordResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return updatePasswordResponseLiveData
    }
}