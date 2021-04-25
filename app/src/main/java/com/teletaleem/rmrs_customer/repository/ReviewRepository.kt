package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.RestaurantDetailResponse
import com.teletaleem.rmrs_customer.data_class.review.Review
import com.teletaleem.rmrs_customer.data_class.review.ReviewResponse
import com.teletaleem.rmrs_customer.data_class.review.reviewlist.ReviewListResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ReviewRepository {

    private lateinit var reviewResponseLiveData: MutableLiveData<ReviewResponse>
    private lateinit var reviewListResponseLiveData: MutableLiveData<ReviewListResponse>

    fun getReviewResponseLiveData(review: Review): LiveData<ReviewResponse> {
        reviewResponseLiveData= MutableLiveData<ReviewResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.submitReview(review)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reviewResponse: ReviewResponse?=null

                reviewResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReviewResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReviewResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reviewResponse"))
                reviewResponseLiveData.postValue(reviewResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return reviewResponseLiveData
    }

    fun getReviewResponseLiveData(restaurantId:String): LiveData<ReviewListResponse> {
        reviewListResponseLiveData= MutableLiveData<ReviewListResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getReviewList(restaurantId)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reviewResponse: ReviewListResponse?=null

                reviewResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReviewListResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReviewListResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reviewResponse"))
                reviewListResponseLiveData.postValue(reviewResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return reviewListResponseLiveData
    }
}