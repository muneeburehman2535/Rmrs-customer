package com.comcept.rmrscustomer.ui.home.verifyInvoice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePassword
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePasswordResponse
import com.comcept.rmrscustomer.data_class.verifyInvoice.RestaurantListResponse
import com.comcept.rmrscustomer.data_class.verifyInvoice.VerifyInvoice
import com.comcept.rmrscustomer.data_class.verifyInvoice.VerifyInvoiceResponse
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.repository.UpdatePasswordRepository
import com.comcept.rmrscustomer.repository.VerifyInvoiceRepository

class VerifyInvoiceViewModel(application: Application) : AndroidViewModel(application) {


    private var verifyInvoiceRepository: VerifyInvoiceRepository = VerifyInvoiceRepository()



    fun verifyInvoiceResponse(verifyInvoice: VerifyInvoice): LiveData<Response<VerifyInvoiceResponse>> {
        return verifyInvoiceRepository.getInvoiceResponseLiveData(verifyInvoice)
    }


    fun getRestaurantList():LiveData<Response<RestaurantListResponse>>{
        return verifyInvoiceRepository.getRestaurantListResponseLiveData()
    }
}