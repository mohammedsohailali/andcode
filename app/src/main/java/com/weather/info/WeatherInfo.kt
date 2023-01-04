package com.weather.info

import android.app.Application
import com.weather.info.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WeatherInfo : Application(), HasAndroidInjector {

    /**
     * Kindly @see [dagger.android.DispatchingAndroidInjector]
     * */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    /**
     * Kindly @see [dagger.android.DispatchingAndroidInjector]
     * */
    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        app = this
    }

    companion object {

        /**
         * This will provide instance of [FlipScoreApp]
         */
        lateinit var app: WeatherInfo
            private set
    }
}