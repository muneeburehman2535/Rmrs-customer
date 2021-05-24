package com.teletaleem.rmrs_customer.ui.reservation.myreservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teletaleem.rmrs_customer.data_class.reservation.Reservation
import com.teletaleem.rmrs_customer.data_class.reservation.ReservationResponse
import com.teletaleem.rmrs_customer.repository.ReservationRepository

class MyReservationsViewModel : ViewModel() {
    private var reservationRepository: ReservationRepository = ReservationRepository()
    fun getReviewListResponse(customerID:String): LiveData<ReservationResponse> {
        return reservationRepository.getReservationListResponseLiveData(customerID)
    }
}