package com.comcept.rmrscustomer.ui.reservation.myreservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrscustomer.data_class.reservation.Reservation
import com.comcept.rmrscustomer.data_class.reservation.ReservationResponse
import com.comcept.rmrscustomer.repository.ReservationRepository
import com.comcept.rmrscustomer.repository.Response

class MyReservationsViewModel : ViewModel() {
    private var reservationRepository: ReservationRepository = ReservationRepository()
    fun getReviewListResponse(customerID:String): LiveData<Response<ReservationResponse>> {
        return reservationRepository.getReservationListResponseLiveData(customerID)
    }
}