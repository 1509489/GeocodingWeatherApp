package com.pixelart.geocodingweatherapp.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.geocodingweatherapp.data.database.LocationDatabase
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity

class RepositoryImpl(private val database: LocationDatabase): Repository {
    
    override fun addLocation(location: LocationEntity) {
        Thread{
            database.getLocationDao().insert(location)
        }.start()
    }
    
    override fun getAllLocations(): LiveData<List<LocationEntity>> =
        database.getLocationDao().getAllLocations()
}