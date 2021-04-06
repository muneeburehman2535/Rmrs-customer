package com.teletaleem.rmrs_customer.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teletaleem.rmrs_customer.data_class.home.category.CategoryResponse
import com.teletaleem.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.repository.HomeRepository
import com.teletaleem.rmrs_customer.repository.LoginRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {


    private var homeRepository: HomeRepository = HomeRepository()

    fun getCategoryResponse():LiveData<CategoryResponse>{
        return homeRepository.getLoginResponseLiveData()
    }

    fun getRestaurantsResponse(categoryId:String):LiveData<RestaurantsResponse>{
        return homeRepository.getRestaurantResponseLiveData(categoryId)
    }

}