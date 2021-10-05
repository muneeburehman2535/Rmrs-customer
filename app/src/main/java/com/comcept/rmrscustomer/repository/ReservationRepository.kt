package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.reservation.Reservation
import com.comcept.rmrscustomer.data_class.reservation.ReservationResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ReservationRepository {

    private lateinit var reservationResponseLiveData: MutableLiveData<ReservationResponse>

    fun getReservationResponseLiveData(reservation: Reservation): LiveData<ReservationResponse> {
        reservationResponseLiveData= MutableLiveData<ReservationResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.addReservation(reservation)?.enqueue(object : Callback<ResponseBody?> {
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

    fun getReservationListResponseLiveData(customerID: String): LiveData<ReservationResponse> {
        reservationResponseLiveData= MutableLiveData<ReservationResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getReservationList(customerID)?.enqueue(object : Callback<ResponseBody?> {
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