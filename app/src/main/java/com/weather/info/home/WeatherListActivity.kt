package com.weather.info.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.realapp.util.makeSnackBar
import com.weather.info.R
import com.weather.info.api.LiveDataObserver
import com.weather.info.base.BaseBindingActivity
import com.weather.info.databinding.ActivityWeatherListBinding
import com.weather.info.model.ResWeatherInfo
import com.weather.info.model.WeatherInfoModel
import com.weather.info.util.Constant
import javax.inject.Inject

class WeatherListActivity : BaseBindingActivity<ActivityWeatherListBinding>() {

    lateinit var mBinding: ActivityWeatherListBinding

    @Inject
    lateinit var viewModel: WeatherInfoViewModel

    private var cityName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = intent?.getStringExtra("city_name").toString()
        getWeatherInfo()
        mBinding.tvHeading.text = cityName
        mBinding.ivBack.setOnClickListener { onBackPressed() }
    }

    override fun layoutId() = R.layout.activity_weather_list

    override fun initializeBinding(binding: ActivityWeatherListBinding) {
        binding.lifecycleOwner = this
        mBinding = binding
    }

    companion object {
        fun newIntent(context: Context, cityName: String): Intent {
            return Intent(context, WeatherListActivity::class.java).putExtra("city_name", cityName)
        }
    }

    private fun getWeatherInfo() {
        viewModel.getWeatherInfo(cityName).observe(this, object :
            LiveDataObserver<ResWeatherInfo>(), ListenerWeatherList {
            override fun onError(message: String?) {

            }

            override fun onSuccess(data: ResWeatherInfo?, message: String?) {
                Log.e("done", data.toString())
                mBinding.recyclerView.adapter = data?.let { AdapterWeatherList(it, this) }
            }

            override fun onLoading() {
            }

            override fun notAuthorized() {

                makeSnackBar(Constant.DEFAULT_UNAUTHORISED_ERROR, mBinding.root)
            }

            override fun onWeatherItemClicked(weatherInfoModel: WeatherInfoModel) {
                startActivity(
                    WeatherInfoActivity.newIntent(
                        this@WeatherListActivity,
                        cityName,
                        weatherInfoModel
                    )
                )
            }
        })
    }
}