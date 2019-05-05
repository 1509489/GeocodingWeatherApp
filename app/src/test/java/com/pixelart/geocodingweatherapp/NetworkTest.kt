package com.pixelart.geocodingweatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.pixelart.geocodingweatherapp.data.database.LocationDatabase
import com.pixelart.geocodingweatherapp.data.dto.geocoding.*
import com.pixelart.geocodingweatherapp.data.dto.weather.*
import com.pixelart.geocodingweatherapp.data.network.NetworkService
import com.pixelart.geocodingweatherapp.data.repository.LocationRepositoryImpl
import com.pixelart.geocodingweatherapp.data.repository.WeatherRepositoryImpl
import com.pixelart.geocodingweatherapp.ui.forecastscreen.WeatherViewModel
import com.pixelart.geocodingweatherapp.ui.homescreen.LocationViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import java.util.concurrent.Executor

class NetworkTest {
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var locationRepository: LocationRepositoryImpl
    private lateinit var weatherRepository: WeatherRepositoryImpl

    private lateinit var locationResponse: GeoResponse
    private lateinit var weatherResponse: WeatherResponse
    private val coordinates = Pair(22.25, 44.22)

    private val networkService: NetworkService = mock()
    private val locationDatabase: LocationDatabase = mock()
    private val locationStatusObserver: Observer<LocationRepositoryImpl.Status> = mock()
    private val locationResponseObserver: Observer<GeoResponse> = mock()
    private val weatherResponseObserver: Observer<WeatherResponse> = mock()
    private val weatherStatusObserver: Observer<WeatherRepositoryImpl.Status> = mock()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupSchedulers(){
            val scheduler = object : Scheduler(){
                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        }
    }

    @Before
    fun setUp(){
        locationRepository = LocationRepositoryImpl(locationDatabase, networkService)
        locationViewModel = LocationViewModel(locationRepository)

        weatherRepository = WeatherRepositoryImpl(networkService)
        weatherViewModel = WeatherViewModel(weatherRepository)


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


        val city = City(1, "Some City", Coord(coordinates.first, coordinates.second),
            "Some Country", 1000)

        val weatherList = ArrayList<WeatherList>()
        weatherResponse = WeatherResponse("", 0.0, 1, weatherList, city)
    }

    @Test
    fun testSuccess(){
        whenever(networkService.getLocation(anyString(), anyString())).thenReturn(Single.just(locationResponse))
        locationViewModel.getLocationNetwork("some address").observeForever(locationResponseObserver)

        val locationCaptor = argumentCaptor<GeoResponse>()
        verify(locationResponseObserver).onChanged(locationCaptor.capture())
        assertTrue(locationCaptor.allValues.size == 1)
        val location = locationCaptor.firstValue.results[0].formattedAddress
        assertEquals("Some Address", location)

        whenever(networkService.getWeatherForecast(anyDouble(), anyDouble(), anyString(), anyString()))
            .thenReturn(Single.just(weatherResponse))
        weatherViewModel.getForecast(coordinates.first, coordinates.second, "metric").observeForever(weatherResponseObserver)

        val weatherCaptor = argumentCaptor<WeatherResponse>()
        verify(weatherResponseObserver).onChanged(weatherCaptor.capture())
        assertEquals(weatherCaptor.firstValue.city.coord.lat, coordinates.first, 0.0)
        assertEquals(weatherCaptor.firstValue.city.name, "Some City")
    }

    @Test
    fun testFailure(){
        val throwable: Throwable = mock()
        whenever(networkService.getLocation(anyString(), anyString())).thenReturn(Single.error(throwable))
        locationViewModel.getLocationNetwork("some address").observeForever(locationResponseObserver)
        locationViewModel.getStatus().observeForever(locationStatusObserver)

        val statusCaptor = argumentCaptor<LocationRepositoryImpl.Status>()
        verify(locationStatusObserver).onChanged(statusCaptor.capture())
        assertEquals(LocationRepositoryImpl.Status.FAILURE, statusCaptor.firstValue)

        whenever(networkService.getWeatherForecast(anyDouble(), anyDouble(), anyString(), anyString()))
            .thenReturn(Single.error(throwable))
        weatherViewModel.getForecast(coordinates.first, coordinates.second, "metric").observeForever(weatherResponseObserver)

        val weatherStatusCaptor = argumentCaptor<WeatherRepositoryImpl.Status>()
        weatherViewModel.getStatus().observeForever(weatherStatusObserver)

        verify(weatherStatusObserver).onChanged(weatherStatusCaptor.capture())
        assertEquals(WeatherRepositoryImpl.Status.FAILURE, weatherStatusCaptor.firstValue)
    }
}