package com.pixelart.geocodingweatherapp.di.network

import com.pixelart.geocodingweatherapp.common.GEO_BASE_URL
import com.pixelart.geocodingweatherapp.common.WEATHER_BASE_URL
import com.pixelart.geocodingweatherapp.data.network.NetworkService
import com.pixelart.geocodingweatherapp.di.application.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule{

    @Provides
    @ApplicationScope
    fun providesInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @ApplicationScope
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @ApplicationScope
    @Named("geocoding")
    fun providesRetrofitClientGeocoding(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GEO_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*@Provides
    @ApplicationScope
    @Named("weather")
    fun providesRetrofitClientWeather(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WEATHER_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }*/

    @Provides
    @ApplicationScope
    @Named("geocoding")
    fun providesNetworkServiceGeocoding(@Named("geocoding")retrofit: Retrofit) = retrofit.create(NetworkService::class.java)

    /*@Provides
    @ApplicationScope
    @Named("weather")
    fun providesNetworkServiceWeather(@Named("weather")retrofit: Retrofit) = retrofit.create(NetworkService::class.java)*/
}