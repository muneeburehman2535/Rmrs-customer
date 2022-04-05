package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePassword
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePasswordResponse
import com.comcept.rmrscustomer.data_class.verifyInvoice.VerifyInvoice
import com.comcept.rmrscustomer.data_class.verifyInvoice.VerifyInvoiceResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class VerifyInvoiceRepository {


    private lateinit var verifyInvoiceResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<VerifyInvoiceResponse>>
    //private val retrofitClass:RetrofitClass= RetrofitClass().getHomeRequestsInstance()!!

    fun getInvoiceResponseLiveData(verifyInvoice: VerifyInvoice): LiveData<com.comcept.rmrscustomer.repository.Response<VerifyInvoiceResponse>> {
        verifyInvoiceResponseLiveData = MutableLiveData<com.comcept.rmrscustomer.repository.Response<VerifyInvoiceResponse>>()

        verifyInvoiceResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.verifyInvoice(verifyInvoice)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var verifyInvoiceResponse: VerifyInvoiceResponse? = null

                verifyInvoiceResponse = if (response.body() == null) {
                    Gson().fromJson(
                        response.errorBody()?.string(),
                        VerifyInvoiceResponse::class.java
                    )

                } else {
                    Gson().fromJson(ConvertResponseToString.getString(response), VerifyInvoiceResponse::class.java
                    )

                }
                Timber.d(Gson().toJson("Update Password: @{verifyInvoiceResponse)}"))
                verifyInvoiceResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(verifyInvoiceResponse))

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                verifyInvoiceResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return verifyInvoiceResponseLiveData
    }
}