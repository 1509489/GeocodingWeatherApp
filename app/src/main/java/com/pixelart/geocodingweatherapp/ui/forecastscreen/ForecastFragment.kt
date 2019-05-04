package com.pixelart.geocodingweatherapp.ui.forecastscreen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pixelart.geocodingweatherapp.R
import kotlinx.android.synthetic.main.fragment_forecast.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pixelart.geocodingweatherapp.AppController
import com.pixelart.geocodingweatherapp.di.fragment.FragmentModule
import javax.inject.Inject

@Suppress("CAST_NEVER_SUCCEEDS")
class ForecastFragment : Fragment() {

    @Inject lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentComponent = (activity?.application as AppController)
            .applicationComponent
            .newFragmentComponent(FragmentModule(this))
        fragmentComponent.injectForecastScreen(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = ""
        main_collapsing.isTitleEnabled = true
        main_collapsing.title = "Forecast"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getForecast(51.5073509, -0.1277583).observe(this, Observer {

        })
    }

}
