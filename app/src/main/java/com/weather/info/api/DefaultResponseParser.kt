package com.weather.info.api

import com.weather.info.util.Constant
import com.google.gson.Gson
import com.google.gson.JsonObject

/**
 * A generic class that returns error in String format if contains [error] in [JsonObject]
 */
class DefaultResponseParser(response: String?) {

    private val gson = Gson()
    private val errorObject = try {
        gson.fromJson(response, JsonObject::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        val jsonObject = JsonObject()
        jsonObject.addProperty("detail", Constant.DEFAULT_SERVER_ERROR)
        jsonObject
    }

    fun getMessage(): String {
        return if (errorObject?.get("detail") != null) {
            errorObject.get("detail").asString
        } else {
            errorObject.toString()
        }
    }

}