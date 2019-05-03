package com.pixelart.geocodingweatherapp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pixelart.geocodingweatherapp.data.repository.RepositoryImpl
import com.pixelart.geocodingweatherapp.ui.homescreen.LocationViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory @Inject constructor(val repository: RepositoryImpl): ViewModelProvider.Factory {
    
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LocationViewModel::class.java)) LocationViewModel(repository) as T
        else throw IllegalArgumentException("ViewModel not found")
    }
}