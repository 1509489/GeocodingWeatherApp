package com.pixelart.geocodingweatherapp.data.dto.weather


data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int
)