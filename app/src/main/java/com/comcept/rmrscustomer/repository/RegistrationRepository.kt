package com.comcept.rmrscustomer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.comcept.rmrscustomer.conversions.ConvertResponseToString
import com.comcept.rmrscustomer.data_class.send_otp.SendOTP
import com.comcept.rmrscustomer.data_class.send_otp.SendOTPResponse
import com.comcept.rmrscustomer.data_class.email_mobile.EmailMobileVerification
import com.comcept.rmrscustomer.data_class.email_mobile.EmailMobileVerificationResponse
import com.comcept.rmrscustomer.data_class.registration.Registration
import com.comcept.rmrscustomer.data_class.registration.RegistrationResponse
import com.comcept.rmrscustomer.network.RetrofitClass
import com.comcept.rmrscustomer.network.WebRequestGeo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RegistrationRepository {

    private lateinit var emailMobileResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<EmailMobileVerificationResponse>>
    private lateinit var registrationResponseLiveData: MutableLiveData<RegistrationResponse>
    private lateinit var otpResponseLiveData: MutableLiveData<com.comcept.rmrscustomer.repository.Response<SendOTPResponse>>
    private val retrofitClass: WebRequestGeo? = RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()


    /*
    * Email and Mobile Number Verification API Call Method
    * */
    fun sendEmailMobileVerificationRequest(emailMobileVerification: EmailMobileVerification): LiveData<com.comcept.rmrscustomer.repository.Response<EmailMobileVerificationResponse>> {

        Timber.d(Gson().toJson(emailMobileVerification))

        emailMobileResponseLiveData= MutableLiveData<com.comcept.rmrscustomer.repository.Response<EmailMobileVerificationResponse>>()

        emailMobileResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Loading())
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.verifyEmailMobile(emailMobileVerification)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                var emailMobileResponse:EmailMobileVerificationResponse?=null

                emailMobileResponse = if (response.body()==null) {
                    Gson().fromJson(response.errorBody()?.string(),EmailMobileVerificationResponse::class.java)

                } else{
                    Gson().fromJson(ConvertResponseToString.getString(response),EmailMobileVerificationResponse::class.java)
                }

                Timber.d("Email : ${Gson().toJson(emailMobileResponse).toString()}")
                emailMobileResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Success(emailMobileResponse))
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               Timber.e(t.message.toString())
                emailMobileResponseLiveData.postValue(com.comcept.rmrscustomer.repository.Response.Error(t.message.toString()))

            }
        })
        return emailMobileResponseLiveData
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