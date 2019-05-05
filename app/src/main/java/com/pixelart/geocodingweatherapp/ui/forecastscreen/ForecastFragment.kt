package com.pixelart.geocodingweatherapp.ui.forecastscreen


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pixelart.geocodingweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pixelart.geocodingweatherapp.AppController
import com.pixelart.geocodingweatherapp.common.*
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.data.model.Forecast
import com.pixelart.geocodingweatherapp.di.fragment.FragmentModule
import com.pixelart.geocodingweatherapp.ui.MainActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_forecast.view.*
import javax.inject.Inject

@Suppress("CAST_NEVER_SUCCEEDS")
class ForecastFragment : Fragment() {

    @Inject lateinit var viewModel: WeatherViewModel

    private lateinit var rootView: View
    private lateinit var latLon: Pair<Double, Double>
    private var toolbarTitle = ""
    private lateinit var forecast: ArrayList<Forecast>
    private var units = ""
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = MainActivity()

        val fragmentComponent = (activity?.application as AppController)
            .applicationComponent
            .newFragmentComponent(FragmentModule(this))
        fragmentComponent.injectForecastScreen(this)

        RxBus.INSTANCE.subscribe(Consumer {
            if (it is LocationEntity){
                latLon = Pair(it.latitude, it.longitude)
                toolbarTitle = it.locationName
            }
        })

        if (PrefsManager.INSTANCE.getTemperatureUnits() != null)
            units = PrefsManager.INSTANCE.getTemperatureUnits()!!

        forecast = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_forecast, container, false)
        return rootView
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = ""
        rootView.collapsing.isTitleEnabled = true
        rootView.collapsing.title = toolbarTitle
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        mainActivity.countingIdlingResource.increment()
        viewModel.getForecast(latLon.first, latLon.second, units)
            .observe(this, Observer { weatherResponse ->

            for (i in 0 until weatherResponse.list.size step 8 )
            {
                forecast.add(Forecast(weatherResponse.list[i].dt, weatherResponse.list[i].weather[0].icon,
                    weatherResponse.list[i].main.temp))
            }

            rootView.apply {
                tvDayOne.text = Utils.INSTANCE.timestampToDayLong(forecast[0].timestamp.toLong())
                tvDayTwo.text = Utils.INSTANCE.timestampToDayShort(forecast[1].timestamp.toLong())
                tvDayThree.text = Utils.INSTANCE.timestampToDayShort(forecast[2].timestamp.toLong())
                tvDayFour.text = Utils.INSTANCE.timestampToDayShort(forecast[3].timestamp.toLong())

                if (units.contains("metric")){
                    tvDayOneTemperature.text = "${forecast[0].temperature}°C"
                    tvDayTwoTemperature.text = "${forecast[1].temperature}°C"
                    tvDayThreeTemperature.text = "${forecast[2].temperature}°C"
                    tvDayFourTemperature.text = "${forecast[3].temperature}°C"
                }
                else if(units.contains("imperial")){
                    tvDayOneTemperature.text = "${forecast[0].temperature}°F"
                    tvDayTwoTemperature.text = "${forecast[1].temperature}°F"
                    tvDayThreeTemperature.text = "${forecast[2].temperature}°F"
                    tvDayFourTemperature.text = "${forecast[3].temperature}°F"
                }

                GlideApp.with(this)
                    .load("$WEATHER_ICONURL${forecast[0].icon}.png")
                    .override(100, 100)
                    .into(ivDayOneIcon)

                GlideApp.with(this)
                    .load("$WEATHER_ICONURL${forecast[1].icon}.png")
                    .override(60, 60)
                    .into(ivDayTwoIcon)

                GlideApp.with(this)
                    .load("$WEATHER_ICONURL${forecast[2].icon}.png")
                    .override(60, 60)
                    .into(ivDayThreeIcon)

                GlideApp.with(this)
                    .load("$WEATHER_ICONURL${forecast[3].icon}.png")
                    .override(60, 60)
                    .into(ivDayFourIcon)
            }

        })
        mainActivity.countingIdlingResource.decrement()
    }

}
