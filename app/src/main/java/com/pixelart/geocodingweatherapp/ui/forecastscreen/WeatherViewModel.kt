package com.pixelart.geocodingweatherapp.ui.forecastscreen

import androidx.lifecycle.ViewModel
import com.pixelart.geocodingweatherapp.data.repository.WeatherRepositoryImpl

class WeatherViewModel(private val weatherRepository: WeatherRepositoryImpl): ViewModel() {

    fun getForecast(latitude: Double, longitude: Double, units: String) =
        weatherRepository.getWeatherForecast(latitude, longitude, units)

    fun getStatus() = weatherRepository.getStatus()

    override fun onCleared() {
        super.onCleared()
        weatherRepository.onClear()
    }
}