package com.pixelart.geocodingweatherapp.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.data.repository.RepositoryImpl

class LocationViewModel(private val repository: RepositoryImpl): ViewModel() {
    
    fun getLocations():LiveData<List<LocationEntity>> = repository.getAllLocations()
    fun addLocation(location: LocationEntity)= repository.addLocation(location)
}