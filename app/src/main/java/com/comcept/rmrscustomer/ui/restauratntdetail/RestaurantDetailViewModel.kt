package com.comcept.rmrscustomer.ui.restauratntdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrscustomer.data_class.login.Login
import com.comcept.rmrscustomer.data_class.login.LoginResponse
import com.comcept.rmrscustomer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrscustomer.data_class.restaurantdetail.deals.DealsResponse
import com.comcept.rmrscustomer.data_class.restaurantdetail.restaurantdetail.RestaurantCategoryResponse
import com.comcept.rmrscustomer.repository.LoginRepository
import com.comcept.rmrscustomer.repository.RestaurantDetailRepository

class RestaurantDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var restaurantDetailRepository: RestaurantDetailRepository = RestaurantDetailRepository()
    fun getRestaurantDetailResponse(restaurantId:String): LiveData<RestaurantDetailResponse> {
        return restaurantDetailRepository.getRestaurantDetailResponseLiveData(restaurantId)
    }
    fun getRestaurantCategoryResponse(restaurantId:String): LiveData<RestaurantCategoryResponse> {
        return restaurantDetailRepository.getRestaurantCategoryResponseLiveData(restaurantId)
    }
    fun getRestaurantDealsResponse(restaurantId:String): LiveData<DealsResponse> {
        return restaurantDetailRepository.getDealsResponseLiveData(restaurantId)
    }
}