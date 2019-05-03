package com.pixelart.geocodingweatherapp.data.network

import com.pixelart.geocodingweatherapp.data.dto.geocoding.GeoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("maps/api/geocode/json")
    fun getLocation(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Single<GeoResponse>
}