package com.comcept.rmrs_customer.network;

import com.comcept.rmrs_customer.data_class.checkout.Checkout;
import com.comcept.rmrs_customer.data_class.confirm_otp.ConfirmOtp;
import com.comcept.rmrs_customer.data_class.email_mobile.EmailMobileVerification;
import com.comcept.rmrs_customer.data_class.email_mobile.EmailMobileVerificationResponse;
import com.comcept.rmrs_customer.data_class.fcm.FcmNotification;
import com.comcept.rmrs_customer.data_class.login.Login;
import com.comcept.rmrs_customer.data_class.login.LoginResponse;
import com.comcept.rmrs_customer.data_class.profile.Profile;
import com.comcept.rmrs_customer.data_class.registration.Registration;
import com.comcept.rmrs_customer.data_class.registration.RegistrationResponse;
import com.comcept.rmrs_customer.data_class.reservation.Reservation;
import com.comcept.rmrs_customer.data_class.review.Review;
import com.comcept.rmrs_customer.data_class.send_otp.SendOTP;
import com.comcept.rmrs_customer.data_class.updatepassword.UpdatePassword;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("/customer/categories")
    Call<ResponseBody> getCategoriesList();

    @GET("/customer/home/?")
    Call<ResponseBody> getHomeData(@Query("categoryID") String categoryID);

    @GET("/customer/restaurant-detail/?")
    Call<ResponseBody> getRestaurantDetail(@Query("restaurantid") String restaurantid);
    @POST("/customer/place-order")
    Call<ResponseBody> checkoutUser(@Body Checkout checkout);

    @GET("/customer/my-order/?")
    Call<ResponseBody> myOrders(@Query("CustomerID") String CustomerID);

    @GET("/customer/get-order/?")
    Call<ResponseBody> getOrderDetail(@Query("CustomerID") String CustomerId,@Query("OrderID") String OrderId);

    @POST("/customer/review")
    Call<ResponseBody> submitReview(@Body Review review);

    @GET("/customer/review")
    Call<ResponseBody> getReviewList(@Query("RestaurantID") String restaurantId);

    @GET("/customer/simple_search/?")
    Call<ResponseBody> getSearchList(@Query("search") String search);

    @POST("/customer/reservation")
    Call<ResponseBody> addReservation(@Body Reservation reservation);

    @GET("/customer/reservation/?")
    Call<ResponseBody> getReservationList(@Query("CustomerID") String CustomerId);

    @POST("/customer/customer-profile")
    Call<ResponseBody> updateCustomerProfile(@Body Profile profile);

    @POST("/customer/change-password")
    Call<ResponseBody> updateCustomerPassword(@Body UpdatePassword updatePassword);

    @POST("/customer/fcm-notification")
    Call<ResponseBody> updateFCMToken(@Body FcmNotification fcmNotification);

    @GET("/customer/getLuckyDrawPoints/?")
    Call<ResponseBody> getLuckyDrawPoints(@Query("CustomerID") String CustomerID);
}
