package com.teletaleem.rmrs_customer.ui.reservation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.reservation.Reservation
import com.teletaleem.rmrs_customer.data_class.reservation.ReservationResponse
import com.teletaleem.rmrs_customer.data_class.review.Review
import com.teletaleem.rmrs_customer.data_class.review.ReviewResponse
import com.teletaleem.rmrs_customer.repository.ReservationRepository

class ReservationViewModel(application: Application): AndroidViewModel(application) {

    private var reservationRepository: ReservationRepository = ReservationRepository()
    fun getReviewResponse(reservation: Reservation): LiveData<ReservationResponse> {
        return reservationRepository.getReservationResponseLiveData(reservation)
    }
}