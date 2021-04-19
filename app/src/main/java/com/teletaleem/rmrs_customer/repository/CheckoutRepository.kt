package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.checkout.Checkout
import com.teletaleem.rmrs_customer.data_class.checkout.checkout_response.CheckoutResponse
import com.teletaleem.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CheckoutRepository {

    private lateinit var checkoutResponseLiveData: MutableLiveData<CheckoutResponse>

    fun getCheckoutResponseLiveData(checkout:Checkout): LiveData<CheckoutResponse> {
        checkoutResponseLiveData=MutableLiveData<CheckoutResponse>()
        Timber.d("Checkout API: ${Gson().toJson(checkout)}")
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.checkoutUser(checkout)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantsResponse: CheckoutResponse?=null

                restaurantsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), CheckoutResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), CheckoutResponse::class.java)

                }
                Timber.d(Gson().toJson(restaurantsResponse).toString())
                checkoutResponseLiveData.postValue(restaurantsResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return checkoutResponseLiveData
    }
}