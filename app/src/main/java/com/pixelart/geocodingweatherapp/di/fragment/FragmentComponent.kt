package com.pixelart.geocodingweatherapp.di.fragment

import com.pixelart.geocodingweatherapp.factories.LocationViewModelFactory
import com.pixelart.geocodingweatherapp.ui.addlocationscreen.AddLocationFragment
import com.pixelart.geocodingweatherapp.ui.homescreen.HomeFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    
    fun getLocationViewModelFactory(): LocationViewModelFactory
    fun injectHomeScreen(homeFragment: HomeFragment)
    fun injectAddLocationScreen(addLocationFragment: AddLocationFragment)
}