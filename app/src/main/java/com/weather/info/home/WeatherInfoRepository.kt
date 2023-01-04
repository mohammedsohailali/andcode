package com.weather.info.home

import androidx.lifecycle.LiveData
import com.weather.info.api.*
import com.weather.info.model.ResWeatherInfo
import com.weather.info.util.Constant
import javax.inject.Inject

class WeatherInfoRepository @Inject constructor(
    var appExecutors: AppExecutors,
    var webServiceInterface: WebServiceInterface
) {
    fun getWeatherInfo(cityName: String): LiveData<Resource<ResWeatherInfo>> {
        return object : NetworkBoundResource<ResWeatherInfo>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<ResWeatherInfo>> {
                return webServiceInterface.getWeatherInfo(cityName, Constant.APP_ID)
            }
        }.asLiveData()
    }
}