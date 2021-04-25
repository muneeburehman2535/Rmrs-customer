package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.searchresponse.SearchResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SearchRepository  {

    private lateinit var searchResponseLiveData: MutableLiveData<SearchResponse>

    fun getSearchResponseLiveData(searchQuery: String): LiveData<SearchResponse> {
        searchResponseLiveData= MutableLiveData<SearchResponse>()
        //Timber.d("Checkout API: ${Gson().toJson(searchQuery)}")
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getSearchList(searchQuery)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var searchResponse: SearchResponse?=null

                searchResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), SearchResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), SearchResponse::class.java)

                }
                Timber.d(Gson().toJson(searchResponse).toString())
                searchResponseLiveData.postValue(searchResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return searchResponseLiveData
    }
}