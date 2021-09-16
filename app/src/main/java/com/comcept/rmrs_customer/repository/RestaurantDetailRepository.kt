package com.comcept.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrs_customer.conversions.ConvertResponseToString
import com.comcept.rmrs_customer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RestaurantDetailRepository {


    private lateinit var restaurantDetailResponseLiveData: MutableLiveData<RestaurantDetailResponse>

    fun getRestaurantDetailResponseLiveData(restaurantId:String): LiveData<RestaurantDetailResponse> {
        restaurantDetailResponseLiveData= MutableLiveData<RestaurantDetailResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getRestaurantDetail(restaurantId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantDetailResponse: RestaurantDetailResponse?=null

                restaurantDetailResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RestaurantDetailResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RestaurantDetailResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $restaurantDetailResponse"))
                restaurantDetailResponseLiveData.postValue(restaurantDetailResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return restaurantDetailResponseLiveData
    }
}