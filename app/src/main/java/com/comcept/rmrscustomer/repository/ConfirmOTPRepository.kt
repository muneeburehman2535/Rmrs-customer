package com.comcept.rmrscustomer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.confirm_otp.ConfirmOtp
import com.comcept.rmrscustomer.data_class.confirm_otp.OTPVerificationResponse
import com.comcept.rmrscustomer.data_class.email_mobile.EmailMobileVerificationResponse
import com.comcept.rmrscustomer.data_class.registration.Registration
import com.comcept.rmrscustomer.data_class.registration.RegistrationResponse
import com.comcept.rmrscustomer.data_class.send_otp.SendOTP
import com.comcept.rmrscustomer.data_class.send_otp.SendOTPResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ConfirmOTPRepository {

    private lateinit var otpVerificationResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<OTPVerificationResponse>>
    private lateinit var registrationResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<RegistrationResponse>>
    private lateinit var otpResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<SendOTPResponse>>



    /*
    * Send OTP API Call Method
    * */
    fun sendOtpVerificationRequest(confirmOtp: ConfirmOtp): LiveData<com.comcept.rmrscustomer.repository.Response<OTPVerificationResponse>> {

        Timber.d(Gson().toJson(confirmOtp))

        otpVerificationResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<OTPVerificationResponse>>()

        otpVerificationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.verifyOtp(confirmOtp)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var otpResponse: OTPVerificationResponse?=null

                otpResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), OTPVerificationResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), OTPVerificationResponse::class.java)

                }
                Timber.d("OTP Verification: ${Gson().toJson(otpResponse)}")
                otpVerificationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(otpResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e(t.message.toString())
                otpVerificationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return otpVerificationResponseLiveData
    }


    /*
    * Registration API Call Method
    * */
    fun sendSignUpResponseLiveData(registration: Registration): LiveData<com.comcept.rmrscustomer.repository.Response<RegistrationResponse>> {
        registrationResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<RegistrationResponse>>()

        registrationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.signUpUser(registration)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var signUpResponse: RegistrationResponse?=null

                signUpResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RegistrationResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RegistrationResponse::class.java)

                }
                Timber.d("Registration: ${Gson().toJson(signUpResponse)}")
                registrationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(signUpResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                //loginResponseLiveData.postValue()
                registrationResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return registrationResponseLiveData
    }

    /*
   * Send OTP API Call Method
   * */
    fun sendOtpRequest(sendOTP: SendOTP): LiveData<com.comcept.rmrscustomer.repository.Response<SendOTPResponse>> {

        Timber.d(Gson().toJson(sendOTP))

        otpResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<SendOTPResponse>>()

        otpResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())

        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.sendOTP(sendOTP)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var otpResponse: SendOTPResponse?=null

                otpResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(),SendOTPResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response),SendOTPResponse::class.java)

                }
                Timber.d("Send OTP Response: ${Gson().toJson(otpResponse).toString()}")
                otpResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(otpResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e(t.message.toString())
                otpResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))
            }
        })
        return otpResponseLiveData
    }
}