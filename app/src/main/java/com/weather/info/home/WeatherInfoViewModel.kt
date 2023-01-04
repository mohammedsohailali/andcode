package com.weather.info.home

import androidx.lifecycle.LiveData
import com.weather.info.api.Resource
import com.weather.info.base.BaseViewModel
import com.weather.info.model.ResWeatherInfo
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor(var weatherInfoRepository: WeatherInfoRepository) :
    BaseViewModel() {
    fun getWeatherInfo(cityName: String): LiveData<Resource<ResWeatherInfo>> {
        return weatherInfoRepository.getWeatherInfo(cityName)
    }
}