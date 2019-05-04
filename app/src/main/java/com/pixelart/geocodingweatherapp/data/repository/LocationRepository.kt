package com.pixelart.geocodingweatherapp.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.geocodingweatherapp.data.dto.geocoding.GeoResponse
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity

interface LocationRepository {
    
    fun addLocation(location: LocationEntity)
    fun getAllLocations():LiveData<List<LocationEntity>>

    fun getLocationNetwork(address: String):LiveData<GeoResponse>
}