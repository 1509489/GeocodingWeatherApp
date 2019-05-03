package com.pixelart.geocodingweatherapp.data.database

import androidx.room.Database
import com.pixelart.geocodingweatherapp.common.DATABASE_VERSION
import com.pixelart.geocodingweatherapp.data.dao.LocationDao
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity

@Database(entities = [LocationEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class LocationDatabase {
    
    abstract fun getLocationDao(): LocationDao
}