package com.teletaleem.rmrs_customer.ui.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.confirm_otp.ConfirmOtp
import com.teletaleem.rmrs_customer.data_class.confirm_otp.OTPVerificationResponse
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.data_class.registration.RegistrationResponse
import com.teletaleem.rmrs_customer.repository.ConfirmOTPRepository


class ConfirmOTPViewModel(application: Application):AndroidViewModel(application) {

    private var confirmOTPRepository: ConfirmOTPRepository = ConfirmOTPRepository()

    fun getOTPVerificationResponse(sendOTP: ConfirmOtp): LiveData<OTPVerificationResponse?> {
        return confirmOTPRepository.sendOtpVerificationRequest(sendOTP)
    }

    fun getSignUpResponse(registration: Registration): LiveData<RegistrationResponse?> {
        return confirmOTPRepository.sendSignUpResponseLiveData(registration)
    }


}