package com.comcept.rmrs_customer.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.comcept.rmrs_customer.data_class.home.category.CategoryResponse
import com.comcept.rmrs_customer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrs_customer.db.dataclass.Favourite
import com.comcept.rmrs_customer.repository.HomeRepository
import com.comcept.rmrs_customer.db.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject
constructor(application: Application, private val roomDBRepository: RoomDBRepository) : AndroidViewModel(application) {


    private var homeRepository: HomeRepository = HomeRepository()
    private val TIME_OUT:Long = 1000
    private lateinit var restaurantResponseLiveData: MutableLiveData<RestaurantsResponse>
    var restaurantId:String="0"

    fun getCategoryResponse():LiveData<CategoryResponse>{
        return homeRepository.getLoginResponseLiveData()
    }

    fun getRestaurantsResponse(categoryId: String,Latitude:Double,Longitude:Double):LiveData<RestaurantsResponse>{
        return homeRepository.getRestaurantResponseLiveData(categoryId,Latitude,Longitude)
    }

    fun insertFavourite(favourite: Favourite){
        viewModelScope.launch {
            roomDBRepository.insertFavouriteItem(favourite)
        }

    }

//    val getRestaurantById:LiveData<Favourite>get() = roomDBRepository.searchRestaurantById(restaurantId).f

    fun deleteFavouriteRecord(favourite: Favourite){
        viewModelScope.launch {
          roomDBRepository.deleteFavouriteItem(favourite)
        }
    }

}