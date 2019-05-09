package com.pixelart.geocodingweatherapp

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pixelart.geocodingweatherapp.data.dto.weather.City
import com.pixelart.geocodingweatherapp.data.dto.weather.Coord
import com.pixelart.geocodingweatherapp.data.dto.weather.WeatherList
import com.pixelart.geocodingweatherapp.data.dto.weather.WeatherResponse
import com.pixelart.geocodingweatherapp.data.network.NetworkService
import com.pixelart.geocodingweatherapp.data.repository.WeatherRepositoryImpl
import com.pixelart.geocodingweatherapp.ui.forecastscreen.WeatherViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class WeatherViewModelTest: BaseTest() {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherRepository: WeatherRepositoryImpl
    private lateinit var weatherResponse: WeatherResponse
    private val coordinates = Pair(22.25, 44.22)

    private val weatherResponseObserver: Observer<WeatherResponse> = mock()
    private val weatherStatusObserver: Observer<WeatherRepositoryImpl.Status> = mock()
    private val networkService: NetworkService = mock()

    @Before
    fun setUp(){
        weatherRepository = WeatherRepositoryImpl(networkService)
        weatherViewModel = WeatherViewModel(weatherRepository)

        val city = City(1, "Some City", Coord(coordinates.first, coordinates.second),
            "Some Country", 1000)

        val weatherList = ArrayList<WeatherList>()
        weatherResponse = WeatherResponse("", 0.0, 1, weatherList, city)
    }

    @Test
    fun testSuccess(){
        whenever(networkService.getWeatherForecast(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(Single.just(weatherResponse))
        weatherViewModel.getForecast(coordinates.first, coordinates.second, "metric").observeForever(weatherResponseObserver)

        val weatherCaptor = argumentCaptor<WeatherResponse>()
        verify(weatherResponseObserver).onChanged(weatherCaptor.capture())
        Assert.assertEquals(weatherCaptor.firstValue.city.coord.lat, coordinates.first, 0.0)
        Assert.assertEquals(weatherCaptor.firstValue.city.name, "Some City")
    }

    @Test
    fun testFailure(){
        val throwable: Throwable = mock()
        whenever(networkService.getWeatherForecast(
            ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        ))
            .thenReturn(Single.error(throwable))
        weatherViewModel.getForecast(coordinates.first, coordinates.second, "metric").observeForever(weatherResponseObserver)

        val weatherStatusCaptor = argumentCaptor<WeatherRepositoryImpl.Status>()
        weatherViewModel.getStatus().observeForever(weatherStatusObserver)

        verify(weatherStatusObserver).onChanged(weatherStatusCaptor.capture())
        Assert.assertEquals(WeatherRepositoryImpl.Status.FAILURE, weatherStatusCaptor.firstValue)
    }
}