package com.comcept.rmrscustomer.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.send_otp.SendOTP
import com.comcept.rmrscustomer.data_class.send_otp.SendOTPResponse
import com.comcept.rmrscustomer.data_class.email_mobile.EmailMobileVerification
import com.comcept.rmrscustomer.data_class.email_mobile.EmailMobileVerificationResponse
import com.comcept.rmrscustomer.repository.RegistrationRepository
import com.comcept.rmrscustomer.repository.Response

class RegistrationViewModel(activity: Application) :AndroidViewModel(activity) {

    private var registrationRepository: RegistrationRepository = RegistrationRepository()


    fun getEmailMobileResponse(emailMobileVerification: EmailMobileVerification): LiveData<Response<EmailMobileVerificationResponse>> {
        return registrationRepository.sendEmailMobileVerificationRequest(emailMobileVerification)
    }



    fun getOTPResponse(sendOTP: SendOTP): LiveData<Response<SendOTPResponse>> {
        return registrationRepository.sendOtpRequest(sendOTP)
    }
}