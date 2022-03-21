package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.checkout.Checkout
import com.comcept.rmrscustomer.data_class.checkout.checkout_response.CheckoutResponse
import com.comcept.rmrscustomer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CheckoutRepository {

    private lateinit var checkoutResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<CheckoutResponse>>

    fun getCheckoutResponseLiveData(checkout:Checkout): LiveData<com.comcept.rmrscustomer.repository.Response<CheckoutResponse>> {
        checkoutResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<CheckoutResponse>>()
        Timber.d("Checkout API: ${Gson().toJson(checkout)}")

        checkoutResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.checkoutUser(checkout)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantsResponse: CheckoutResponse?=null

                restaurantsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), CheckoutResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), CheckoutResponse::class.java)

                }
                Timber.d(Gson().toJson(restaurantsResponse).toString())
                checkoutResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(restaurantsResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")

                checkoutResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))


            }
        })
        return checkoutResponseLiveData
    }
}