package com.comcept.rmrs_customer.conversions

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object ConvertStringToJSON {

    fun getObject(response:String): JSONObject  {

        val jsonObject = JSONObject(response)

        return jsonObject
    }


    fun getArray(response:String): JSONArray {

        val jsonArray = JSONArray(response)
        return jsonArray
    }

}