package com.pixelart.geocodingweatherapp.data.dto.weather


import com.google.gson.annotations.SerializedName

data class Main(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Double,
    @SerializedName("sea_level")
    val seaLevel: Double,
    @SerializedName("grnd_level")
    val grndLevel: Double,
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Double
)