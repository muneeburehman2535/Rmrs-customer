package com.teletaleem.rmrs_customer.shared_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teletaleem.rmrs_customer.data_class.registration.Registration

class RegistrationSharedViewModel:ViewModel() {

    val mRegistration = MutableLiveData<Registration>()

    fun sendRegistrationData(registration: Registration) {
        mRegistration.value = registration
    }
}