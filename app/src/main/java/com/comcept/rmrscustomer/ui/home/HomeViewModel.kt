package com.comcept.rmrscustomer.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.comcept.rmrscustomer.data_class.home.category.CategoryResponse
import com.comcept.rmrscustomer.data_class.home.restaurants.RestaurantsResponse
import com.comcept.rmrscustomer.db.dataclass.Favourite
import com.comcept.rmrscustomer.repository.HomeRepository
import com.comcept.rmrscustomer.db.repository.RoomDBRepository
import com.comcept.rmrscustomer.repository.Response
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

    fun getCategoryResponse():LiveData<Response<CategoryResponse>>{
        return homeRepository.getLoginResponseLiveData()
    }

    fun getRestaurantsResponse(categoryId: String,Latitude:Double,Longitude:Double):LiveData<Response<RestaurantsResponse>>{
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