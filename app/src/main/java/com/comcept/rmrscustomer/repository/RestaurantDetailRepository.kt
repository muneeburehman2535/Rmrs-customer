package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrscustomer.data_class.restaurantdetail.deals.DealsResponse
import com.comcept.rmrscustomer.data_class.restaurantdetail.restaurantdetail.RestaurantCategoryResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RestaurantDetailRepository {


    private lateinit var restaurantDetailResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<RestaurantDetailResponse>>
    private lateinit var restaurantCategoryResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<RestaurantCategoryResponse>>
    private lateinit var dealsResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<DealsResponse>>

    fun getRestaurantDetailResponseLiveData(restaurantId:String): LiveData<com.comcept.rmrscustomer.repository.Response<RestaurantDetailResponse>> {
        restaurantDetailResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<RestaurantDetailResponse>>()

        restaurantDetailResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getRestaurantDetail(restaurantId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantDetailResponse: RestaurantDetailResponse?=null

                restaurantDetailResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RestaurantDetailResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RestaurantDetailResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $restaurantDetailResponse"))
                restaurantDetailResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(restaurantDetailResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                restaurantDetailResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))

            }
        })
        return restaurantDetailResponseLiveData
    }

    fun getRestaurantCategoryResponseLiveData(restaurantId:String): LiveData<com.comcept.rmrscustomer.repository.Response<RestaurantCategoryResponse>> {
        restaurantCategoryResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<RestaurantCategoryResponse>>()

        restaurantCategoryResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getRestaurantCategories(restaurantId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var restaurantCategoryResponse: RestaurantCategoryResponse?=null

                restaurantCategoryResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RestaurantCategoryResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RestaurantCategoryResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $restaurantCategoryResponse"))
                restaurantCategoryResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(restaurantCategoryResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                restaurantCategoryResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))

            }
        })
        return restaurantCategoryResponseLiveData
    }

    fun getDealsResponseLiveData(restaurantId:String): LiveData<com.comcept.rmrscustomer.repository.Response<DealsResponse>> {
        dealsResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<DealsResponse>>()

        dealsResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getDealsData(restaurantId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var dealsResponse: DealsResponse?=null

                dealsResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), DealsResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), DealsResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $dealsResponse"))
                dealsResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(dealsResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                dealsResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))

            }
        })
        return dealsResponseLiveData
    }
}