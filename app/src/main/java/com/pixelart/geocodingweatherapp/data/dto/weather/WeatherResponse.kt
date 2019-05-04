package com.pixelart.geocodingweatherapp.data.dto.weather


data class WeatherResponse(
    val cod: String,
    val message: Double,
    val cnt: Int,
    val list: List<WeatherList>,
    val city: City
)