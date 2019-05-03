package com.pixelart.geocodingweatherapp.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity

interface Repository {
    
    fun addLocation(location: LocationEntity)
    fun getAllLocations():LiveData<List<LocationEntity>>
}