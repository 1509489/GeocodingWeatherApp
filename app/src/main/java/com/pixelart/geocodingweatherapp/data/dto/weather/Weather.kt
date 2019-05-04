package com.pixelart.geocodingweatherapp.data.dto.weather


data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)