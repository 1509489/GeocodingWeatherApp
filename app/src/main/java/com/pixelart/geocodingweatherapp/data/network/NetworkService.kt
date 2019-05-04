package com.pixelart.geocodingweatherapp.data.network

import com.pixelart.geocodingweatherapp.data.dto.geocoding.GeoResponse
import com.pixelart.geocodingweatherapp.data.dto.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("maps/api/geocode/json")
    fun getLocation(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Single<GeoResponse>

    @GET("data/2.5/forecast")
    fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("appid") appId: String
    ):Single<WeatherResponse>
}