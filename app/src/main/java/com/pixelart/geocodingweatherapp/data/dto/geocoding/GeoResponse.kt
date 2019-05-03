package com.pixelart.geocodingweatherapp.data.dto.geocoding

data class GeoResponse(
    val results: List<Result>,
    val status: String
)