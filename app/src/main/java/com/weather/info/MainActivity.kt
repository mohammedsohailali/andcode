package com.weather.info

import android.os.Bundle
import android.util.Log
import com.android.realapp.util.makeSnackBar
import com.weather.info.api.LiveDataObserver
import com.weather.info.base.BaseActivity
import com.weather.info.base.BaseBindingActivity
import com.weather.info.databinding.ActivityMainBinding
import com.weather.info.home.WeatherInfoViewModel
import com.weather.info.home.WeatherListActivity
import com.weather.info.model.ResWeatherInfo
import com.weather.info.util.Constant.Companion.DEFAULT_UNAUTHORISED_ERROR
import javax.inject.Inject

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun layoutId() = R.layout.activity_main

    lateinit var mBinding: ActivityMainBinding

    override fun initializeBinding(binding: ActivityMainBinding) {
        binding.lifecycleOwner = this
        mBinding = binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.tvLookUp.setOnClickListener { openListActivity() }
    }

    private fun openListActivity() {
        if (mBinding.edtCityName.text.toString().isNotEmpty()) {
            startActivity(WeatherListActivity.newIntent(this, mBinding.edtCityName.text.toString()))
        } else {
            mBinding.edtCityName.requestFocus()
            mBinding.edtCityName.error = "Please enter city name"
        }
    }

}