package com.pixelart.geocodingweatherapp

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pixelart.geocodingweatherapp.data.database.LocationDatabase
import com.pixelart.geocodingweatherapp.data.dto.geocoding.*
import com.pixelart.geocodingweatherapp.data.network.NetworkService
import com.pixelart.geocodingweatherapp.data.repository.LocationRepositoryImpl
import com.pixelart.geocodingweatherapp.ui.homescreen.LocationViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class LocationViewModelTest : BaseTest(){
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationRepository: LocationRepositoryImpl
    private lateinit var locationResponse: GeoResponse
    private val coordinates = Pair(22.25, 44.22)

    private val networkService: NetworkService = mock()
    private val locationDatabase: LocationDatabase = mock()
    private val locationStatusObserver: Observer<LocationRepositoryImpl.Status> = mock()
    private val locationResponseObserver: Observer<GeoResponse> = mock()

    @Before
    fun setUp(){
        locationRepository = LocationRepositoryImpl(locationDatabase, networkService)
        locationViewModel = LocationViewModel(locationRepository)

        val results = ArrayList<Result>()
        val addressComponent = ArrayList<AddressComponent>()
        val northeast = Northeast(coordinates.first, coordinates.second)
        val southwest = Southwest(coordinates.first, coordinates.second)
        val viewport = Viewport(northeast, southwest)
        val bounds = Bounds(northeast, southwest)
        val location = Location(coordinates.first, coordinates.second)
        val geometry = Geometry(bounds,location,"", viewport)

        addressComponent.add(AddressComponent("Some Address", "SM", emptyList()))
        results.add(Result(addressComponent, "Some Address", geometry, "", emptyList()))
        locationResponse = GeoResponse(results, "")
    }

    @Test
    fun testSuccess(){
        whenever(networkService.getLocation(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(
            Single.just(locationResponse))
        locationViewModel.getLocationNetwork("some address").observeForever(locationResponseObserver)

        val locationCaptor = argumentCaptor<GeoResponse>()
        verify(locationResponseObserver).onChanged(locationCaptor.capture())
        Assert.assertTrue(locationCaptor.allValues.size == 1)
        val location = locationCaptor.firstValue.results[0].formattedAddress
        Assert.assertEquals("Some Address", location)
    }

    @Test
    fun testFailure(){
        val throwable: Throwable = mock()
        whenever(networkService.getLocation(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Single.error(throwable))
        locationViewModel.getLocationNetwork("some address").observeForever(locationResponseObserver)
        locationViewModel.getStatus().observeForever(locationStatusObserver)

        val statusCaptor = argumentCaptor<LocationRepositoryImpl.Status>()
        verify(locationStatusObserver).onChanged(statusCaptor.capture())
        Assert.assertEquals(LocationRepositoryImpl.Status.FAILURE, statusCaptor.firstValue)
    }
}