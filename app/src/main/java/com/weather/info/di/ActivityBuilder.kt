package com.weather.info.di

import com.weather.info.MainActivity
import com.weather.info.home.WeatherInfoActivity
import com.weather.info.home.WeatherListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Helps to generate an {@link AndroidInjector} for all activities
 * */
@Suppress("unused")
@Module
abstract class ActivityBuilder {

    /**
     * fun to bind Activity, making Injection enable
     * */
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindWeatherListActivity(): WeatherListActivity

    @ContributesAndroidInjector
    abstract fun bindWeatherInfoActivity(): WeatherInfoActivity


//    @ContributesAndroidInjector(modules = [(DashboardActivityBuilderModule::class)])
//    abstract fun bindingDashBoardActivity(): ActivityDashBoard

}