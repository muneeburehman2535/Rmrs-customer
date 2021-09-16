package com.comcept.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrs_customer.conversions.ConvertResponseToString
import com.comcept.rmrs_customer.data_class.fcm.FCMTokenResponse
import com.comcept.rmrs_customer.data_class.fcm.FcmNotification
import com.comcept.rmrs_customer.data_class.home.category.CategoryResponse
import com.comcept.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrs_customer.data_class.luckydrawpoints.LuckyDrawPointsResponse
import com.comcept.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeRepository {

    private lateinit var categoryResponseLiveData: MutableLiveData<CategoryResponse>
    private lateinit var restaurantResponseLiveData: MutableLiveData<RestaurantsResponse>
    private lateinit var fcmResponseLiveData: MutableLiveData<FCMTokenResponse>
    private lateinit var luckyDrawPointsResponseLiveData: MutableLiveData<LuckyDrawPointsResponse>

    fun getLoginResponseLiveData(): LiveData<CategoryResponse> {
        categoryResponseLiveData=MutableLiveData<CategoryResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.categoriesList?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var categoryResponse: CategoryResponse?=null

                categoryResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), CategoryResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), CategoryResponse::class.java)

                }
                Timber.d(Gson().toJson(categoryResponse).toString())
                categoryResponseLiveData.postValue(categoryResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return categoryResponseLiveData
    }


    fun getRestaurantResponseLiveData(categoryId:String): LiveData<RestaurantsResponse> {
        restaurantResponseLiveData=MutableLiveData<RestaurantsResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getHomeData(categoryId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantsResponse: RestaurantsResponse?=null

                restaurantsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RestaurantsResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RestaurantsResponse::class.java)

                }
                Timber.d(Gson().toJson(restaurantsResponse).toString())
                restaurantResponseLiveData.postValue(restaurantsResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return restaurantResponseLiveData
    }

    fun updateFCMTokenResponseLiveData(fcmNotification: FcmNotification): LiveData<FCMTokenResponse> {
        fcmResponseLiveData=MutableLiveData<FCMTokenResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.updateFCMToken(fcmNotification)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var fcmTokenResponse: FCMTokenResponse?=null

                fcmTokenResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), FCMTokenResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), FCMTokenResponse::class.java)

                }
                Timber.d(Gson().toJson(fcmTokenResponse).toString())
                fcmResponseLiveData.postValue(fcmTokenResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return fcmResponseLiveData
    }

    fun luckyDrawPointsResponseLiveData(customerID:String): LiveData<LuckyDrawPointsResponse> {
        luckyDrawPointsResponseLiveData=MutableLiveData<LuckyDrawPointsResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getLuckyDrawPoints(customerID)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var luckyDrawPointsResponse: LuckyDrawPointsResponse?=null

                luckyDrawPointsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), LuckyDrawPointsResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), LuckyDrawPointsResponse::class.java)

                }
                Timber.d(Gson().toJson(luckyDrawPointsResponse).toString())
                luckyDrawPointsResponseLiveData.postValue(luckyDrawPointsResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return luckyDrawPointsResponseLiveData
    }
}