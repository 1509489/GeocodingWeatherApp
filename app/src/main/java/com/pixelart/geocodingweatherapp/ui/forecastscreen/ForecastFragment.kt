package com.pixelart.geocodingweatherapp.ui.forecastscreen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pixelart.geocodingweatherapp.R
import kotlinx.android.synthetic.main.fragment_forecast.*
import androidx.appcompat.app.AppCompatActivity

@Suppress("CAST_NEVER_SUCCEEDS")
class ForecastFragment : Fragment() {

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

}
