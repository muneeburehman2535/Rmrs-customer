package com.comcept.rmrscustomer.ui.otpverification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.confirm_otp.ConfirmOtp
import com.comcept.rmrscustomer.data_class.confirm_otp.OTPVerificationResponse
import com.comcept.rmrscustomer.data_class.registration.Registration
import com.comcept.rmrscustomer.data_class.registration.RegistrationResponse
import com.comcept.rmrscustomer.data_class.send_otp.SendOTP
import com.comcept.rmrscustomer.data_class.send_otp.SendOTPResponse
import com.comcept.rmrscustomer.repository.ConfirmOTPRepository
import com.comcept.rmrscustomer.repository.Response


class ConfirmOTPViewModel(application: Application):AndroidViewModel(application) {

    private var confirmOTPRepository: ConfirmOTPRepository = ConfirmOTPRepository()

    fun getOTPVerificationResponse(sendOTP: ConfirmOtp): LiveData<Response<OTPVerificationResponse>> {
        return confirmOTPRepository.sendOtpVerificationRequest(sendOTP)
    }

    fun getSignUpResponse(registration: Registration): LiveData<Response<RegistrationResponse>> {
        return confirmOTPRepository.sendSignUpResponseLiveData(registration)
    }

    fun getOTPResponse(sendOTP: SendOTP): LiveData<Response<SendOTPResponse>> {
        return confirmOTPRepository.sendOtpRequest(sendOTP)
    }

}