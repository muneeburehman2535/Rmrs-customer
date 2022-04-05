package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.searchresponse.SearchResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SearchRepository  {

    private lateinit var searchResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<SearchResponse>>

    fun getSearchResponseLiveData(searchQuery: String,Latitude:Double,Longitude:Double): LiveData<com.comcept.rmrscustomer.repository.Response<SearchResponse>> {
        searchResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<SearchResponse>>()
        //Timber.d("Checkout API: ${Gson().toJson(searchQuery)}")

        searchResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getSearchList(searchQuery,Latitude,Longitude)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var searchResponse: SearchResponse?=null

                searchResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), SearchResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), SearchResponse::class.java)

                }
                Timber.d(Gson().toJson(searchResponse).toString())
                searchResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(searchResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                searchResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return searchResponseLiveData
    }
}