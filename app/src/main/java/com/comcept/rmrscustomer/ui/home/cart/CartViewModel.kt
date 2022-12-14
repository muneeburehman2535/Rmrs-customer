package com.comcept.rmrscustomer.ui.home.cart

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.db.repository.RoomDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel
@Inject
constructor(application: Application, private val roomDBRepository: RoomDBRepository) : ViewModel() {



    fun updateCart(cart: Cart){
        viewModelScope.launch {
            roomDBRepository.updateItem(cart)
        }
    }

    fun deleteCartItem(cart: Cart)
    {
        viewModelScope.launch {
            roomDBRepository.deleteRecord(cart)
        }
    }
}