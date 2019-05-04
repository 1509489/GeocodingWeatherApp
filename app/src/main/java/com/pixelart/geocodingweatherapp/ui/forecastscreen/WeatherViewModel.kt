package com.pixelart.geocodingweatherapp.ui.forecastscreen

import androidx.lifecycle.ViewModel
import com.pixelart.geocodingweatherapp.data.repository.WeatherRepositoryImpl

class WeatherViewModel(private val weatherRepository: WeatherRepositoryImpl): ViewModel() {

    fun getForecast(latitude: Double, longitude: Double) = weatherRepository.getWeatherForecast(latitude, longitude)
}