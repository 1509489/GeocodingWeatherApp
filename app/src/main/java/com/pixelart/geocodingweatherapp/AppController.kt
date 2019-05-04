package com.pixelart.geocodingweatherapp

import android.app.Application
import com.pixelart.geocodingweatherapp.di.application.ApplicationComponent
import com.pixelart.geocodingweatherapp.di.application.ApplicationModule
import com.pixelart.geocodingweatherapp.di.application.DaggerApplicationComponent
import com.pixelart.geocodingweatherapp.di.network.NetworkModule

class AppController: Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            //.networkModule(NetworkModule())
            .build()
    }
}