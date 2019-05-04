package com.pixelart.geocodingweatherapp.di.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pixelart.geocodingweatherapp.factories.LocationViewModelFactory
import com.pixelart.geocodingweatherapp.factories.WeatherViewModelFactory
import com.pixelart.geocodingweatherapp.ui.forecastscreen.WeatherViewModel
import com.pixelart.geocodingweatherapp.ui.homescreen.LocationViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {
    
    @Provides
    @FragmentScope
    fun providesLocationViewModelGeocoding(viewModelFactory: LocationViewModelFactory) =
        ViewModelProviders.of(fragment, viewModelFactory).get(LocationViewModel::class.java)

    @Provides
    @FragmentScope
    fun providesLocationViewModelWeather(viewModelFactory: WeatherViewModelFactory) =
        ViewModelProviders.of(fragment, viewModelFactory).get(WeatherViewModel::class.java)
}