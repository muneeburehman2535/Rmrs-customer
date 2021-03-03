package com.teletaleem.rmrs_customer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.souq.uae.thebooksouq.Conversions.ConvertResponseToString
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTP
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTPResponse
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.data_class.registration.RegistrationResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import com.teletaleem.rmrs_customer.network.WebRequestGeo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RegistrationRepository {

    private lateinit var emailMobileResponseLiveData: MutableLiveData<EmailMobileVerificationResponse>
    private lateinit var registrationResponseLiveData: MutableLiveData<RegistrationResponse>
    private lateinit var otpResponseLiveData: MutableLiveData<SendOTPResponse>
    private val retrofitClass: WebRequestGeo? = RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()


    /*
    * Email and Mobile Number Verification API Call Method
    * */
    fun getEmailMobileVerificationLiveData(emailMobileVerification: EmailMobileVerification): LiveData<EmailMobileVerificationResponse?> {

        Timber.d(Gson().toJson(emailMobileVerification))

        emailMobileResponseLiveData= MutableLiveData<EmailMobileVerificationResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.verifyEmailMobile(emailMobileVerification)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if (response.body()==null)
                    {
                        var errorResponse: EmailMobileVerificationResponse? = Gson().fromJson(response.errorBody()?.string(),EmailMobileVerificationResponse::class.java)
                        emailMobileResponseLiveData.postValue(errorResponse)
                    }
                else{
                        var jsonResult = ConvertResponseToString.getString(response)
                        var emailMobileResponse: EmailMobileVerificationResponse? = Gson().fromJson(jsonResult,EmailMobileVerificationResponse::class.java)
                        emailMobileResponseLiveData.postValue(emailMobileResponse)
                }


            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               Log.d("api_error",t.message.toString())
            }
        })
        return emailMobileResponseLiveData
    }

    /*
    * Send OTP API Call Method
    * */
    fun getOtpLiveData(sendOTP: SendOTP): LiveData<SendOTPResponse?> {

        Timber.d(Gson().toJson(sendOTP))

        otpResponseLiveData= MutableLiveData<SendOTPResponse>()
        RetrofitClass.getHomeInstance()?.getHomeRequestsInstance()?.sendOTP(sendOTP)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.body()==null)
                {
                    var errorResponse: SendOTPResponse? = Gson().fromJson(response.errorBody()?.string(),SendOTPResponse::class.java)
                    otpResponseLiveData.postValue(errorResponse)
                }
                else{
                    var jsonResult = ConvertResponseToString.getString(response)
                    var otpResponse: SendOTPResponse? = Gson().fromJson(jsonResult,SendOTPResponse::class.java)
                    otpResponseLiveData.postValue(otpResponse)
                }


            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.d("api_error",t.message.toString())
            }
        })
        return otpResponseLiveData
    }

    /*
    * Registration API Call Method
    * */
    fun getSignUpResponseLiveData(registration:Registration): LiveData<RegistrationResponse?> {
        retrofitClass?.signUpUser(registration)?.enqueue(object : Callback<RegistrationResponse?> {
            override fun onResponse(call: Call<RegistrationResponse?>, response: Response<RegistrationResponse?>) {
                registrationResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<RegistrationResponse?>, t: Throwable) {
                //loginResponseLiveData.postValue()
            }
        })
        return registrationResponseLiveData
    }
}