package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.reservation.ReservationResponse
import com.teletaleem.rmrs_customer.data_class.review.Review
import com.teletaleem.rmrs_customer.data_class.review.ReviewResponse
import com.teletaleem.rmrs_customer.data_class.review.reviewlist.ReviewListResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ReservationRespository {

    private lateinit var reservationResponseLiveData: MutableLiveData<ReservationResponse>

    fun getReviewResponseLiveData(review: Review): LiveData<ReservationResponse> {
        reservationResponseLiveData= MutableLiveData<ReservationResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.submitReview(review)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reservationResponse: ReservationResponse?=null

                reservationResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReservationResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReservationResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reservationResponse"))
                reservationResponseLiveData.postValue(reservationResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
            }
        })
        return reservationResponseLiveData
    }
}