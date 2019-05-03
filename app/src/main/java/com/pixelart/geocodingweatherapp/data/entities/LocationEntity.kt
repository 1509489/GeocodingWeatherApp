package com.pixelart.geocodingweatherapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pixelart.geocodingweatherapp.common.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class LocationEntity (
    @PrimaryKey(autoGenerate = true) var id:Int? = null,
    var locationName: String,
    var longitude: Double,
    var latitude: Double,
    var countryLongName: String,
    var countryShortName: String
)