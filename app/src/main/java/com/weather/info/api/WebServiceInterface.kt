package com.weather.info.api

import androidx.lifecycle.LiveData
import com.weather.info.model.ResWeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface WebServiceInterface {


    // User APIs
//    @POST("api/auth/signin")
//    fun login(@Body reqModelCreateUser: ReqModelLogin): LiveData<ApiResponse<ResModelLogin>>
//
//    @POST("api/user")
//    fun createAgent(@Body jsonObject: JsonObject): LiveData<ApiResponse<ResModelCreateAgent>>

    @GET("forecast")
    fun getWeatherInfo(
        @Query("q") cityName: String,
        @Query("appid") appKey: String
    ): LiveData<ApiResponse<ResWeatherInfo>>

//    @GET("api/auth/sendotp/{number}")
//    fun sendOTP(@Path("number") number: String): LiveData<ApiResponse<JsonObject>>

}