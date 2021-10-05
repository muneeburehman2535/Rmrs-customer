package com.comcept.rmrscustomer.conversions

import android.util.Log


import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset

object ConvertResponseToString {

    fun getString(response: Response<ResponseBody?>): String {
        val responseBody = response.body()
        val source = responseBody!!.source()
        try {
            source.request(java.lang.Long.MAX_VALUE) // request the entire body.
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val buffer = source.buffer()
        // clone buffer before reading from it
        val responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"))
        Log.d("res_",responseBodyString)
        Timber.d(responseBodyString)

        return responseBodyString
    }

}