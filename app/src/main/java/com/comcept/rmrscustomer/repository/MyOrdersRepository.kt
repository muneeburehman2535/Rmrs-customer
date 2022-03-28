package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.checkout.checkout_response.CheckoutResponse
import com.comcept.rmrscustomer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrscustomer.data_class.myorders.MyOrdersResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MyOrdersRepository {

    private lateinit var myOrdersResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<MyOrdersResponse>>
    private lateinit var checkoutResponseLiveData:MutableLiveData<com.comcept.rmrscustomer.repository.Response<CheckoutResponse>>

    fun getMyOrdersResponseLiveData(categoryId:String): LiveData<com.comcept.rmrscustomer.repository.Response<MyOrdersResponse>> {
        myOrdersResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<MyOrdersResponse>>()

        myOrdersResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.myOrders(categoryId)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantsResponse: MyOrdersResponse?=null

                restaurantsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), MyOrdersResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), MyOrdersResponse::class.java)

                }
                Timber.d(Gson().toJson(restaurantsResponse).toString())
                myOrdersResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(restaurantsResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                myOrdersResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return myOrdersResponseLiveData
    }

    fun getOrderDetailResponseLiveData(customerId:String,orderId:String): LiveData<com.comcept.rmrscustomer.repository.Response<CheckoutResponse>> {
        checkoutResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<CheckoutResponse>>()

        checkoutResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getOrderDetail(customerId,orderId)?.enqueue(object :
                Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var checkoutResponse: CheckoutResponse?=null

                checkoutResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), CheckoutResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), CheckoutResponse::class.java)

                }
                Timber.d(Gson().toJson(checkoutResponse).toString())
                checkoutResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(checkoutResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                checkoutResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return checkoutResponseLiveData
    }

}