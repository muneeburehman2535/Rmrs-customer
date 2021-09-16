package com.comcept.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrs_customer.conversions.ConvertResponseToString
import com.comcept.rmrs_customer.data_class.confirm_otp.ConfirmOtp
import com.comcept.rmrs_customer.data_class.confirm_otp.OTPVerificationResponse
import com.comcept.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse
import com.comcept.rmrs_customer.data_class.registration.Registration
import com.comcept.rmrs_customer.data_class.registration.RegistrationResponse
import com.comcept.rmrs_customer.data_class.send_otp.SendOTP
import com.comcept.rmrs_customer.data_class.send_otp.SendOTPResponse
import com.comcept.rmrs_customer.network.RetrofitClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ConfirmOTPRepository {

    private lateinit var otpVerificationResponseLiveData: MutableLiveData<OTPVerificationResponse>
    private lateinit var registrationResponseLiveData: MutableLiveData<RegistrationResponse>
    private lateinit var otpResponseLiveData: MutableLiveData<SendOTPResponse>



    /*
    * Send OTP API Call Method
    * */
    fun sendOtpVerificationRequest(confirmOtp: ConfirmOtp): LiveData<OTPVerificationResponse?> {

        Timber.d(Gson().toJson(confirmOtp))

        otpVerificationResponseLiveData= MutableLiveData<OTPVerificationResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.verifyOtp(confirmOtp)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var otpResponse: OTPVerificationResponse?=null

                otpResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), OTPVerificationResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), OTPVerificationResponse::class.java)

                }
                Timber.d("OTP Verification: ${Gson().toJson(otpResponse)}")
                otpVerificationResponseLiveData.postValue(otpResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e(t.message.toString())
            }
        })
        return otpVerificationResponseLiveData
    }


    /*
    * Registration API Call Method
    * */
    fun sendSignUpResponseLiveData(registration: Registration): LiveData<RegistrationResponse?> {
        registrationResponseLiveData= MutableLiveData<RegistrationResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.signUpUser(registration)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var signUpResponse: RegistrationResponse?=null

                signUpResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(), RegistrationResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response), RegistrationResponse::class.java)

                }
                Timber.d("Registration: ${Gson().toJson(signUpResponse)}")
                registrationResponseLiveData.postValue(signUpResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                //loginResponseLiveData.postValue()
            }
        })
        return registrationResponseLiveData
    }

    /*
   * Send OTP API Call Method
   * */
    fun sendOtpRequest(sendOTP: SendOTP): LiveData<SendOTPResponse?> {

        Timber.d(Gson().toJson(sendOTP))

        otpResponseLiveData= MutableLiveData<SendOTPResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.sendOTP(sendOTP)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var otpResponse: SendOTPResponse?=null

                otpResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(),SendOTPResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response),SendOTPResponse::class.java)

                }
                Timber.d("Send OTP Response: ${Gson().toJson(otpResponse).toString()}")
                otpResponseLiveData.postValue(otpResponse)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.e(t.message.toString())
            }
        })
        return otpResponseLiveData
    }
}