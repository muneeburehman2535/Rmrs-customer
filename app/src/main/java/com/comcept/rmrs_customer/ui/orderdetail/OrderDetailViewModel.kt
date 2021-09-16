package com.comcept.rmrs_customer.ui.orderdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrs_customer.data_class.checkout.checkout_response.CheckoutResponse
import com.comcept.rmrs_customer.data_class.myorders.MyOrdersResponse
import com.comcept.rmrs_customer.repository.MyOrdersRepository

class OrderDetailViewModel(application: Application):AndroidViewModel(application) {

    private val myOrdersRepository= MyOrdersRepository()

    fun getOrderDetailResponse(customerId: String,orderId:String): LiveData<CheckoutResponse> {
        return myOrdersRepository.getOrderDetailResponseLiveData(customerId,orderId)
    }
}