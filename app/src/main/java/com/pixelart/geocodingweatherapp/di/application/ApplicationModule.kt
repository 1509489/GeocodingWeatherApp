package com.pixelart.geocodingweatherapp.di.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pixelart.geocodingweatherapp.common.DATABASE_NAME
import com.pixelart.geocodingweatherapp.data.database.LocationDatabase
import com.pixelart.geocodingweatherapp.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
    
    @Provides
    @ApplicationScope
    fun providesContext(): Context = application
    
    @Provides
    @ApplicationScope
    fun providesDatabase(): LocationDatabase = Room.databaseBuilder(application.applicationContext,
        LocationDatabase::class.java, DATABASE_NAME).build()
    
    @Provides
    @ApplicationScope
    fun providesRepository(database: LocationDatabase): RepositoryImpl = RepositoryImpl(database)
}