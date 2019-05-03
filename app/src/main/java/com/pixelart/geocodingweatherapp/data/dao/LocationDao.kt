package com.pixelart.geocodingweatherapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationEntity)
    
    @Query("SELECT * FROM location_table ORDER BY locationName ASC")
    fun getAllLocations():LiveData<List<LocationEntity>>
}