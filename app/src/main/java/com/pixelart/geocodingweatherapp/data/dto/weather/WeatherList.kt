package com.pixelart.geocodingweatherapp.data.dto.weather


import com.google.gson.annotations.SerializedName

data class WeatherList(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val rain: Rain
)