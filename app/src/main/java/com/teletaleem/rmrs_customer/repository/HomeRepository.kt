package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.home.category.CategoryResponse
import com.teletaleem.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeRepository {

    private lateinit var categoryResponseLiveData: MutableLiveData<CategoryResponse>
    private lateinit var restaurantResponseLiveData: MutableLiveData<RestaurantsResponse>

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
}