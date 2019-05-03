package com.pixelart.geocodingweatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pixelart.geocodingweatherapp.common.API_KEY
import com.pixelart.geocodingweatherapp.data.database.LocationDatabase
import com.pixelart.geocodingweatherapp.data.dto.geocoding.GeoResponse
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.data.network.NetworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(private val database: LocationDatabase, private val networkService: NetworkService): Repository {

    private val location = MutableLiveData<GeoResponse>()
    private val compositeDisposable = CompositeDisposable()
    
    override fun addLocation(location: LocationEntity) {
        Thread{
            database.getLocationDao().insert(location)
        }.start()
    }
    
    override fun getAllLocations(): LiveData<List<LocationEntity>> =
        database.getLocationDao().getAllLocations()

    override fun getLocationNetwork(address: String): LiveData<GeoResponse> {

        compositeDisposable.add(
            networkService.getLocation(address, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response -> location.value = response}, {error -> error.printStackTrace()})
        )

        return location
    }

    fun onClear(){
        compositeDisposable.clear()
    }
}