package com.comcept.rmrscustomer.ui.orderdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.checkout.checkout_response.CheckoutResponse
import com.comcept.rmrscustomer.data_class.myorders.MyOrdersResponse
import com.comcept.rmrscustomer.repository.MyOrdersRepository
import com.comcept.rmrscustomer.repository.Response

class OrderDetailViewModel(application: Application):AndroidViewModel(application) {

    private val myOrdersRepository= MyOrdersRepository()

    fun getOrderDetailResponse(customerId: String,orderId:String): LiveData<Response<CheckoutResponse>> {
        return myOrdersRepository.getOrderDetailResponseLiveData(customerId,orderId)
    }
}