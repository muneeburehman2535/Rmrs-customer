package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.teletaleem.rmrs_customer.data_class.myorders.MyOrdersResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MyOrdersRepository {

    private lateinit var myOrdersResponseLiveData: MutableLiveData<MyOrdersResponse>

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

}