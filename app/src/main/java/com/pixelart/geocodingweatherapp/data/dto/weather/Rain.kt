package com.pixelart.geocodingweatherapp.data.dto.weather


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)