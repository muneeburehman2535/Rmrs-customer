package com.comcept.rmrscustomer.ui.myorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrscustomer.data_class.myorders.MyOrdersResponse

import com.comcept.rmrscustomer.repository.MyOrdersRepository

class MyOrdersViewModel : ViewModel() {
    private val myOrdersRepository=MyOrdersRepository()

    fun getMyOrdersResponse(customerId: String): LiveData<MyOrdersResponse> {
        return myOrdersRepository.getMyOrdersResponseLiveData(customerId)
    }
}