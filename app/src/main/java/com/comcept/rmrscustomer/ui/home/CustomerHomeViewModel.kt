package com.comcept.rmrscustomer.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.fcm.FCMTokenResponse
import com.comcept.rmrscustomer.data_class.fcm.FcmNotification
import com.comcept.rmrscustomer.data_class.home.category.CategoryResponse
import com.comcept.rmrscustomer.data_class.luckydrawpoints.LuckyDrawPointsResponse
import com.comcept.rmrscustomer.repository.HomeRepository
import com.comcept.rmrscustomer.repository.Response

class CustomerHomeViewModel(application:Application): AndroidViewModel(application) {

    private var homeRepository: HomeRepository = HomeRepository()
    fun updateFcmTokenResponse(fcmNotification: FcmNotification): LiveData<Response<FCMTokenResponse>> {
        return homeRepository.updateFCMTokenResponseLiveData(fcmNotification)
    }

    fun getLuckyDrawPointsResponse(customerID:String): LiveData<Response<LuckyDrawPointsResponse>> {
        return homeRepository.luckyDrawPointsResponseLiveData(customerID)
    }

}