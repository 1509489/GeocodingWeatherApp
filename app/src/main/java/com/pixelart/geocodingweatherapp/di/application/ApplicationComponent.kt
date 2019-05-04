package com.pixelart.geocodingweatherapp.di.application

import com.pixelart.geocodingweatherapp.di.fragment.FragmentComponent
import com.pixelart.geocodingweatherapp.di.fragment.FragmentModule
import com.pixelart.geocodingweatherapp.di.network.NetworkModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun newFragmentComponent(fragmentModule: FragmentModule): FragmentComponent
}