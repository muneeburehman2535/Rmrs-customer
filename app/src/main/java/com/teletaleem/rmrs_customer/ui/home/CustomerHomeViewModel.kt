package com.teletaleem.rmrs_customer.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.fcm.FCMTokenResponse
import com.teletaleem.rmrs_customer.data_class.fcm.FcmNotification
import com.teletaleem.rmrs_customer.data_class.home.category.CategoryResponse
import com.teletaleem.rmrs_customer.repository.HomeRepository

class CustomerHomeViewModel(application:Application): AndroidViewModel(application) {

    private var homeRepository: HomeRepository = HomeRepository()
    fun updateFcmTokenResponse(fcmNotification: FcmNotification): LiveData<FCMTokenResponse> {
        return homeRepository.updateFCMTokenResponseLiveData(fcmNotification)
    }

}