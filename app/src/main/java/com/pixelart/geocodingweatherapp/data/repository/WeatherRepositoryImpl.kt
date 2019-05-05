package com.pixelart.geocodingweatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pixelart.geocodingweatherapp.common.APP_ID
import com.pixelart.geocodingweatherapp.data.dto.weather.WeatherResponse
import com.pixelart.geocodingweatherapp.data.network.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherRepositoryImpl(private val networkService: NetworkService):WeatherRepository {

    private val weatherResponse = MutableLiveData<WeatherResponse>()
    private val status = MutableLiveData<Status>()
    private val compositeDisposable = CompositeDisposable()

    override fun getWeatherForecast(latitude: Double, longitude: Double, units: String): LiveData<WeatherResponse> {

        compositeDisposable.add(
            networkService.getWeatherForecast(latitude, longitude, units, APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response -> weatherResponse.postValue(response)
                    status.value = Status.SUCCESS},
                    {error -> error.printStackTrace()
                    status.value = Status.FAILURE}
                )
        )

        return weatherResponse
    }

    override fun onClear() {
        compositeDisposable.clear()
    }

    fun getStatus():LiveData<Status> = status

    enum class Status{
        SUCCESS,
        FAILURE
    }
}