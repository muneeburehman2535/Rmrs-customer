package com.comcept.rmrscustomer.ui.myorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.comcept.rmrscustomer.data_class.myorders.MyOrdersResponse

import com.comcept.rmrscustomer.repository.MyOrdersRepository
import com.comcept.rmrscustomer.repository.Response

class MyOrdersViewModel : ViewModel() {
    private val myOrdersRepository=MyOrdersRepository()

    fun getMyOrdersResponse(customerId: String): LiveData<Response<MyOrdersResponse>> {
        return myOrdersRepository.getMyOrdersResponseLiveData(customerId)
    }
}