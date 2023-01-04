package com.weather.info.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.info.home.WeatherInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This class used to bind ViewModels
 * */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    /**
     * To Bind ViewModelFactory
     * */
    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherInfoViewModel::class)
    abstract fun bindWeatherInfoViewModel(viewModel: WeatherInfoViewModel): ViewModel
}