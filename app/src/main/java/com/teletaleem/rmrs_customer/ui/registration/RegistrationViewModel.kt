package com.teletaleem.rmrs_customer.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTP
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTPResponse
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse
import com.teletaleem.rmrs_customer.repository.RegistrationRepository

class RegistrationViewModel(activity: Application) :AndroidViewModel(activity) {

    private var registrationRepository: RegistrationRepository = RegistrationRepository()


    fun getEmailMobileResponse(emailMobileVerification: EmailMobileVerification): LiveData<EmailMobileVerificationResponse?> {
        return registrationRepository.sendEmailMobileVerificationRequest(emailMobileVerification)
    }



    fun getOTPResponse(sendOTP: SendOTP): LiveData<SendOTPResponse?> {
        return registrationRepository.sendOtpRequest(sendOTP)
    }
}