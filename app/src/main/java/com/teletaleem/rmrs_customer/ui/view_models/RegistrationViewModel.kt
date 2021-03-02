package com.teletaleem.rmrs_customer.ui.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.data_class.registration.RegistrationResponse
import com.teletaleem.rmrs_customer.repository.RegistrationRepository

class RegistrationViewModel(activity: Application) :AndroidViewModel(activity) {

    private var registrationRepository: RegistrationRepository = RegistrationRepository()


    fun getEmailMobileResponse(emailMobileVerification: EmailMobileVerification): LiveData<EmailMobileVerificationResponse?> {
        return registrationRepository.getEmailMobileVerificationLiveData(emailMobileVerification)
    }

    fun getSignUpResponse(registration: Registration): LiveData<RegistrationResponse?> {
        return registrationRepository.getSignUpResponseLiveData(registration)
    }
}