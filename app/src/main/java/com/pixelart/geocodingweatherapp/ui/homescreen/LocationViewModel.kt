package com.pixelart.geocodingweatherapp.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pixelart.geocodingweatherapp.data.dto.geocoding.GeoResponse
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.data.repository.LocationRepositoryImpl

class LocationViewModel(private val repository: LocationRepositoryImpl): ViewModel() {
    
    fun getLocations():LiveData<List<LocationEntity>> = repository.getAllLocations()
    fun addLocation(location: LocationEntity)= repository.addLocation(location)

    fun getLocationNetwork(location: String):LiveData<GeoResponse> = repository.getLocationNetwork(location)
    fun getSatus() = repository.getStatus()

     fun clear() {
        super.onCleared()
         onCleared()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onClear()
    }
}