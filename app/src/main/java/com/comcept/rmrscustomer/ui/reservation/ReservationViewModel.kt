package com.comcept.rmrscustomer.ui.reservation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.reservation.Reservation
import com.comcept.rmrscustomer.data_class.reservation.ReservationResponse
import com.comcept.rmrscustomer.data_class.review.Review
import com.comcept.rmrscustomer.data_class.review.ReviewResponse
import com.comcept.rmrscustomer.repository.ReservationRepository

class ReservationViewModel(application: Application): AndroidViewModel(application) {

    private var reservationRepository: ReservationRepository = ReservationRepository()
    fun getReviewResponse(reservation: Reservation): LiveData<ReservationResponse> {
        return reservationRepository.getReservationResponseLiveData(reservation)
    }
}