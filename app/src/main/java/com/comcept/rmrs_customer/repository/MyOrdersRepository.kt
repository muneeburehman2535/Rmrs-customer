package com.comcept.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrs_customer.conversions.ConvertResponseToString
import com.comcept.rmrs_customer.data_class.checkout.checkout_response.CheckoutResponse
import com.comcept.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrs_customer.data_class.myorders.MyOrdersResponse
import com.comcept.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MyOrdersRepository {

    private lateinit var myOrdersResponseLiveData: MutableLiveData<MyOrdersResponse>
    private lateinit var checkoutResponseLiveData:MutableLiveData<CheckoutResponse>

    fun getMyOrdersResponseLiveData(categoryId:String): LiveData<MyOrdersResponse> {
        myOrdersResponseLiveData=MutableLiveData<MyOrdersResponse>()
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
                myOrdersResponseLiveData.postValue(restaurantsResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return myOrdersResponseLiveData
    }

    fun getOrderDetailResponseLiveData(customerId:String,orderId:String): LiveData<CheckoutResponse> {
        checkoutResponseLiveData=MutableLiveData<CheckoutResponse>()
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
                checkoutResponseLiveData.postValue(checkoutResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return checkoutResponseLiveData
    }

}