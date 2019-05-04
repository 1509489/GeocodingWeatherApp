package com.pixelart.geocodingweatherapp.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.geocodingweatherapp.data.dto.weather.WeatherResponse

interface WeatherRepository {

    fun getWeatherForecast(latitude: Double, longitude: Double):LiveData<WeatherResponse>
}