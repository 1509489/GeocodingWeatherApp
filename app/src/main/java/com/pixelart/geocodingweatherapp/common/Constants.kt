package com.pixelart.geocodingweatherapp.common

const val TABLE_NAME = "location_table"
const val DATABASE_VERSION = 1
const val DATABASE_NAME = "location_db"

//Geocoding
var GEO_BASE_URL = "https://maps.googleapis.com/"
const val API_KEY = "AIzaSyAbVL9HHSKAincWQpBfFQxKK6FN0SFRILo"

//Open weather
const val APP_ID = "ebd631a0fa77db0f0181aedad58f17d7"
var WEATHER_BASE_URL = "https://api.openweathermap.org/"
const val WEATHER_ICONURL = "http://openweathermap.org/img/w/"

//Shard Preferences
const val PREFS_NAME = "com.pixelart.geocodingweatherapp.common.sharedpreferences"
const val KEY_UNITS = "units"