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

    private lateinit var reservationResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReservationResponse>>

    fun getReservationResponseLiveData(reservation: Reservation): LiveData<com.comcept.rmrscustomer.repository.Response<ReservationResponse>> {
        reservationResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReservationResponse>>()

        Timber.d("Reservation-Params: ${Gson().toJson(reservation)}")

        reservationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.addReservation(reservation)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reservationResponse: ReservationResponse?=null

                reservationResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReservationResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReservationResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reservationResponse"))
                reservationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(reservationResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                reservationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return reservationResponseLiveData
    }

    fun getReservationListResponseLiveData(customerID: String): LiveData<com.comcept.rmrscustomer.repository.Response<ReservationResponse>> {
        reservationResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<ReservationResponse>>()


        reservationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.getReservationList(customerID)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var reservationResponse: ReservationResponse?=null

                reservationResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), ReservationResponse::class.java)
                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), ReservationResponse::class.java)
                }
                Timber.d(Gson().toJson("Restaurant Detail: $reservationResponse"))
                reservationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(reservationResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e("Error: ${t.message.toString()}")
                reservationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return reservationResponseLiveData
    }
}