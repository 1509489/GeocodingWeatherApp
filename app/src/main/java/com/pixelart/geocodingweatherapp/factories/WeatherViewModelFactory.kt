package com.pixelart.geocodingweatherapp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pixelart.geocodingweatherapp.data.repository.WeatherRepositoryImpl
import com.pixelart.geocodingweatherapp.ui.forecastscreen.WeatherViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory @Inject constructor(private val weatherRepository: WeatherRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(WeatherViewModel::class.java)) WeatherViewModel(weatherRepository) as T
        else throw IllegalArgumentException("Weather ViewModel not found")
    }
}