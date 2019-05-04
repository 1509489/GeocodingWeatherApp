package com.pixelart.geocodingweatherapp.common

import android.app.Application
import android.content.Context

enum class PrefsManager(private var context: Application) {
    INSTANCE;

    constructor()

    fun setTemperatureUnits(units: String){
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(KEY_UNITS, units)
        editor.apply()
    }

    fun getTemperatureUnits():String?{
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_UNITS, null)
    }

    fun setContext(ctx: Application){
        context = ctx
    }
}