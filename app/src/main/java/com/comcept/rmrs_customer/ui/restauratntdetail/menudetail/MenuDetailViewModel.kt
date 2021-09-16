package com.comcept.rmrs_customer.ui.restauratntdetail.menudetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comcept.rmrs_customer.data_class.cart.Cart
import com.comcept.rmrs_customer.data_class.cart.SingleCart
import com.comcept.rmrs_customer.repository.HomeRepository
import com.comcept.rmrs_customer.db.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuDetailViewModel
@Inject
constructor(application: Application, private val roomDBRepository: RoomDBRepository) : ViewModel() {
    private var homeRepository: HomeRepository = HomeRepository()




    fun insertCartItem(cart: Cart){
        viewModelScope.launch {
            roomDBRepository.insertItem(cart)
        }

    }

    fun emptyCartRecord(){
        viewModelScope.launch {
            roomDBRepository.emptyCart()
        }
    }


}