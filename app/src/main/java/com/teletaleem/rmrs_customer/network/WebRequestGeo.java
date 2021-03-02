package com.teletaleem.rmrs_customer.network;

import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification;
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse;
import com.teletaleem.rmrs_customer.data_class.login.Login;
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse;
import com.teletaleem.rmrs_customer.data_class.registration.Registration;
import com.teletaleem.rmrs_customer.data_class.registration.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebRequestGeo {

    @POST("/customer/login")
    Call<LoginResponse> login(@Body Login login);

    @POST
    Call<RegistrationResponse> signUpUser(@Body Registration registration);

    @POST
    Call<EmailMobileVerificationResponse> verifyEmailMobile(@Body EmailMobileVerification emailMobileVerification);



}
