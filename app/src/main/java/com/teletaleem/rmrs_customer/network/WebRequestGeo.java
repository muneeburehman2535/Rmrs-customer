package com.teletaleem.rmrs_customer.network;

import com.teletaleem.rmrs_customer.data_class.confirm_otp.ConfirmOtp;
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification;
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse;
import com.teletaleem.rmrs_customer.data_class.login.Login;
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse;
import com.teletaleem.rmrs_customer.data_class.registration.Registration;
import com.teletaleem.rmrs_customer.data_class.registration.RegistrationResponse;
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTP;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebRequestGeo {

    @POST("/customer/login")
    Call<ResponseBody> login(@Body Login login);

    @POST("/customer/verify-email")
    Call<ResponseBody> verifyEmailMobile(@Body EmailMobileVerification emailMobileVerification);

    @POST("/customer/send-otp")
    Call<ResponseBody> sendOTP(@Body SendOTP sendOTP);

    @POST("/customer/verify-otp")
    Call<ResponseBody> verifyOtp(@Body ConfirmOtp confirmOtp);

    @POST("/customer/signup")
    Call<ResponseBody> signUpUser(@Body Registration registration);




}
