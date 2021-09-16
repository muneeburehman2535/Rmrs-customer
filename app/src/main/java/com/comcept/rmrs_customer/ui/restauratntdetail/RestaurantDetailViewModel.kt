package com.comcept.rmrs_customer.ui.restauratntdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrs_customer.data_class.login.Login
import com.comcept.rmrs_customer.data_class.login.LoginResponse
import com.comcept.rmrs_customer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrs_customer.repository.LoginRepository
import com.comcept.rmrs_customer.repository.RestaurantDetailRepository

class RestaurantDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var restaurantDetailRepository: RestaurantDetailRepository = RestaurantDetailRepository()
    fun getRestaurantDetailResponse(restaurantId:String): LiveData<RestaurantDetailResponse> {
        return restaurantDetailRepository.getRestaurantDetailResponseLiveData(restaurantId)
    }
}