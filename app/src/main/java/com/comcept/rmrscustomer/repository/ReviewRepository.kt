package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.review.Review
import com.comcept.rmrscustomer.data_class.review.ReviewResponse
import com.comcept.rmrscustomer.data_class.review.reviewlist.ReviewListResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ReviewRepository {

    private lateinit var reviewResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReviewResponse>>
    private lateinit var reviewListResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReviewListResponse>>

    fun getReviewResponseLiveData(review: Review): LiveData<com.comcept.rmrscustomer.repository.Response<ReviewResponse>> {
        reviewResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReviewResponse>>()

        reviewResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.submitReview(review)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reviewResponse: ReviewResponse?=null

                reviewResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReviewResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReviewResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reviewResponse"))
                reviewResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(reviewResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                reviewResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))

            }
        })
        return reviewResponseLiveData
    }

    fun getReviewResponseLiveData(restaurantId:String): LiveData<com.comcept.rmrscustomer.repository.Response<ReviewListResponse>> {
        reviewListResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReviewListResponse>>()

        reviewListResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getReviewList(restaurantId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reviewResponse: ReviewListResponse?=null

                reviewResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReviewListResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReviewListResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reviewResponse"))
                reviewListResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(reviewResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                reviewListResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return reviewListResponseLiveData
    }
}