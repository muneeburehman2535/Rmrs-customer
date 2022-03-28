package com.comcept.rmrscustomer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.fcm.FCMTokenResponse
import com.comcept.rmrscustomer.data_class.fcm.FcmNotification
import com.comcept.rmrscustomer.data_class.home.category.CategoryResponse
import com.comcept.rmrscustomer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrscustomer.data_class.luckydrawpoints.LuckyDrawPointsResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeRepository {

    private lateinit var categoryResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<CategoryResponse>>
    private lateinit var restaurantResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<RestaurantsResponse>>
    private lateinit var fcmResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<FCMTokenResponse>>
    private lateinit var luckyDrawPointsResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<LuckyDrawPointsResponse>>

    fun getLoginResponseLiveData(): LiveData<com.comcept.rmrscustomer.repository.Response<CategoryResponse>> {
        categoryResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<CategoryResponse>>()

        categoryResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.categoriesList?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var categoryResponse: CategoryResponse?=null

                categoryResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), CategoryResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), CategoryResponse::class.java)

                }
                Timber.d(Gson().toJson(categoryResponse).toString())
                categoryResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(categoryResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                categoryResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return categoryResponseLiveData
    }

    fun getRestaurantResponseLiveData(categoryId:String,Latitude:Double,Longitude:Double): LiveData<com.comcept.rmrscustomer.repository.Response<RestaurantsResponse>> {
        restaurantResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<RestaurantsResponse>>()

        restaurantResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getHomeData(categoryId,Latitude,Longitude)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantsResponse: RestaurantsResponse?=null

                restaurantsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RestaurantsResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RestaurantsResponse::class.java)

                }
                Timber.d(Gson().toJson(restaurantsResponse).toString())
                Log.d("HomeResponse","${Gson().toJson(restaurantsResponse).toString()}")
                restaurantResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(restaurantsResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                restaurantResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return restaurantResponseLiveData
    }

    fun updateFCMTokenResponseLiveData(fcmNotification: FcmNotification): LiveData<com.comcept.rmrscustomer.repository.Response<FCMTokenResponse>> {
        fcmResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<FCMTokenResponse>>()
        fcmResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.updateFCMToken(fcmNotification)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var fcmTokenResponse: FCMTokenResponse?=null

                fcmTokenResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), FCMTokenResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), FCMTokenResponse::class.java)

                }
                Timber.d(Gson().toJson(fcmTokenResponse).toString())
                fcmResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(fcmTokenResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                fcmResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return fcmResponseLiveData
    }

    fun luckyDrawPointsResponseLiveData(customerID:String): LiveData<com.comcept.rmrscustomer.repository.Response<LuckyDrawPointsResponse>> {
        luckyDrawPointsResponseLiveData=MutableLiveData<com.comcept.rmrscustomer.repository.Response<LuckyDrawPointsResponse>>()

        luckyDrawPointsResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getLuckyDrawPoints(customerID)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var luckyDrawPointsResponse: LuckyDrawPointsResponse?=null

                luckyDrawPointsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), LuckyDrawPointsResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), LuckyDrawPointsResponse::class.java)

                }
                Timber.d(Gson().toJson(luckyDrawPointsResponse).toString())
                luckyDrawPointsResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(luckyDrawPointsResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                luckyDrawPointsResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return luckyDrawPointsResponseLiveData
    }
}