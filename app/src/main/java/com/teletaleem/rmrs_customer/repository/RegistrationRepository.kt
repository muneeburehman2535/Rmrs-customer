package com.teletaleem.rmrs_customer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.data_class.registration.RegistrationResponse
import com.teletaleem.rmrs_customer.network.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationRepository {

    private lateinit var emailMobileResponseLiveData: MutableLiveData<EmailMobileVerificationResponse>
    private lateinit var registrationResponseLiveData: MutableLiveData<RegistrationResponse>
    private val retrofitClass: RetrofitClass = RetrofitClass().getHomeInstance()!!


    /*
    * Email and Mobile Number Verification API Call Method
    * */
    fun getEmailMobileVerificationLiveData(emailMobileVerification: EmailMobileVerification): LiveData<EmailMobileVerificationResponse?> {
        retrofitClass.getHomeRequestsInstance()?.verifyEmailMobile(emailMobileVerification)?.enqueue(object : Callback<EmailMobileVerificationResponse?> {
            override fun onResponse(call: Call<EmailMobileVerificationResponse?>, response: Response<EmailMobileVerificationResponse?>) {
                emailMobileResponseLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<EmailMobileVerificationResponse?>, t: Throwable) {
                //loginResponseLiveData.postValue()
            }
        })
        return emailMobileResponseLiveData
    }

    /*
    * Registration API Call Method
    * */
    fun getSignUpResponseLiveData(registration:Registration): LiveData<RegistrationResponse?> {
        retrofitClass.getHomeRequestsInstance()?.signUpUser(registration)?.enqueue(object : Callback<RegistrationResponse?> {
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