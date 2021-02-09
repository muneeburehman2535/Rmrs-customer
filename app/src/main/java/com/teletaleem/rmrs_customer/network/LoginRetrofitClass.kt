package com.teletaleem.rmrs_customer.network

import com.teletaleem.rmrs_customer.utilities.AppGlobal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginRetrofitClass {

    private var geoInstance: LoginRetrofitClass? = null
    private var homeInstance: LoginRetrofitClass? = null

    private var geoService: WebRequestGeo? = null
    private var homeService: WebRequestGeo? = null



    fun getGeoInstance(): LoginRetrofitClass? {
        if (geoInstance == null) {
            geoInstance = LoginRetrofitClass()
            val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).build().newBuilder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
            val loginRetrofit: Retrofit = Retrofit.Builder().baseUrl(AppGlobal.LOCATION_IQ_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build()
            geoService = loginRetrofit.create(WebRequestGeo::class.java)
            geoService = loginRetrofit.create(WebRequestGeo::class.java)
        }
        return geoInstance
    }

    fun getHomeInstance(): LoginRetrofitClass? {
        if (homeInstance == null) {
            homeInstance = LoginRetrofitClass()
            val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).build().newBuilder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
            val loginRetrofit: Retrofit = Retrofit.Builder().baseUrl(AppGlobal.HOME_BASE_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build()
            homeService = loginRetrofit.create(WebRequestGeo::class.java)
            homeService = loginRetrofit.create(WebRequestGeo::class.java)
        }
        return homeInstance
    }

    fun getGeoWebRequestsInstance(): WebRequestGeo? {
        return geoService
    }

    fun getHomeRequestsInstance(): WebRequestGeo? {
        return homeService
    }
}