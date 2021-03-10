package com.teletaleem.rmrs_customer.network

import com.teletaleem.rmrs_customer.utilities.AppGlobal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClass {



    companion object{
        private var geoInstance: RetrofitClass? = null
        private var homeInstance: RetrofitClass? = null

        private var geoService: WebRequestGeo? = null
        private var homeService: WebRequestGeo? = null

        fun getGeoInstance(): RetrofitClass? {
            if (geoInstance == null) {
                geoInstance = RetrofitClass()
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

        fun getHomeInstance(): RetrofitClass? {
            if (homeInstance == null) {
                homeInstance = RetrofitClass()
                val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).build().newBuilder()
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(loggingInterceptor)
                val loginRetrofit: Retrofit = Retrofit.Builder().baseUrl(AppGlobal.HOME_BASE_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build()
                homeService = loginRetrofit.create(WebRequestGeo::class.java)
                //homeService = loginRetrofit.create(WebRequestGeo::class.java)
            }
            return homeInstance
        }
    }




    fun getGeoWebRequestsInstance(): WebRequestGeo? {
        return geoService
    }

    fun getHomeRequestsInstance(): WebRequestGeo? {
        return homeService
    }


}