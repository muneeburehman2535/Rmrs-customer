package com.teletaleem.rmrs_customer.ui.myorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.teletaleem.rmrs_customer.data_class.myorders.MyOrdersResponse

import com.teletaleem.rmrs_customer.repository.MyOrdersRepository

class MyOrdersViewModel : ViewModel() {
    private val myOrdersRepository=MyOrdersRepository()

    fun getMyOrdersResponse(customerId: String): LiveData<MyOrdersResponse> {
        return myOrdersRepository.getMyOrdersResponseLiveData(customerId)
    }
}