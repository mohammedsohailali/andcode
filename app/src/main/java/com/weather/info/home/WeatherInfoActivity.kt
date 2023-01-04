package com.weather.info.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.realapp.util.kelvinToCelsius
import com.bumptech.glide.Glide
import com.weather.info.R
import com.weather.info.base.BaseBindingActivity
import com.weather.info.databinding.ActivityWeatherInfoBinding
import com.weather.info.model.WeatherInfoModel

class WeatherInfoActivity : BaseBindingActivity<ActivityWeatherInfoBinding>() {

    lateinit var mBinding: ActivityWeatherInfoBinding

    lateinit var weatherInfoModel: WeatherInfoModel


    private var cityName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = intent?.getStringExtra("city_name").toString()
        weatherInfoModel = intent?.getSerializableExtra("info_model") as WeatherInfoModel
        mBinding.tvTemp.text = weatherInfoModel.main.temp.kelvinToCelsius().toString()


        Glide.with(this)
            .load("http://openweathermap.org/img/w/${weatherInfoModel.weather[0].icon}.png")
            .into(mBinding.ivWeatherIcon)

        mBinding.tvMaxMin.text =
            "Max/Min: ${weatherInfoModel.main.tempMax.kelvinToCelsius()}°/${weatherInfoModel.main.tempMin.kelvinToCelsius()}°"

        mBinding.tvFeelsLike.text =
            "Feels Like: ${weatherInfoModel.main.feelsLike.kelvinToCelsius()}°"

        mBinding.tvWeatherName.text = weatherInfoModel.weather[0].main
        mBinding.tvWeatherDesc.text = weatherInfoModel.weather[0].description

        mBinding.tvHeading.text = cityName
        mBinding.ivBack.setOnClickListener { onBackPressed() }
    }

    override fun layoutId() = R.layout.activity_weather_info

    override fun initializeBinding(binding: ActivityWeatherInfoBinding) {
        binding.lifecycleOwner = this
        mBinding = binding
    }

    companion object {
        fun newIntent(
            context: Context,
            cityName: String,
            weatherInfoModel: WeatherInfoModel
        ): Intent {
            return Intent(context, WeatherInfoActivity::class.java).putExtra("city_name", cityName)
                .putExtra("info_model", weatherInfoModel)
        }
    }
}