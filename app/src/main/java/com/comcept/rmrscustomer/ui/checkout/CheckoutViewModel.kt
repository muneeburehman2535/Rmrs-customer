package com.comcept.rmrscustomer.ui.checkout

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comcept.rmrscustomer.data_class.checkout.Checkout
import com.comcept.rmrscustomer.data_class.checkout.checkout_response.CheckoutResponse
import com.comcept.rmrscustomer.repository.CheckoutRepository
import com.comcept.rmrscustomer.db.repository.RoomDBRepository
import com.comcept.rmrscustomer.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel
    @Inject
        constructor(application: Application, private val roomDBRepository: RoomDBRepository) : ViewModel() {

    private var homeRepository: CheckoutRepository = CheckoutRepository()

    fun getCheckoutResponse(checkout: Checkout): LiveData<Response<CheckoutResponse>> {
        return homeRepository.getCheckoutResponseLiveData(checkout)
    }

    fun emptyCartRecord(){
        viewModelScope.launch {
            roomDBRepository.emptyCart()
        }
    }
}