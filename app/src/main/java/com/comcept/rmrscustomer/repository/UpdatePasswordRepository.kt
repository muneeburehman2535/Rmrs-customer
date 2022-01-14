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

    private lateinit var updatePasswordResponseLiveData: MutableLiveData<UpdatePasswordResponse>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getUpdatePasswordResponseLiveData(updatePassword: UpdatePassword): LiveData<UpdatePasswordResponse> {
        updatePasswordResponseLiveData= MutableLiveData<UpdatePasswordResponse>()
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
                updatePasswordResponseLiveData.postValue(updatePasswordResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return updatePasswordResponseLiveData
    }
}