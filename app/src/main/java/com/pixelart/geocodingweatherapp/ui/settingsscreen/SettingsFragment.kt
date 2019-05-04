package com.pixelart.geocodingweatherapp.ui.settingsscreen


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.common.PrefsManager
import com.pixelart.geocodingweatherapp.common.RxBus
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (PrefsManager.INSTANCE.getTemperatureUnits() != null && PrefsManager.INSTANCE.getTemperatureUnits() == "imperial") {
            switchFahrenheit.isChecked = true
            switchFahrenheit.text = activity?.applicationContext?.resources?.getString(R.string.switch_celsius)
        }

        switchFahrenheit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                switchFahrenheit.text = activity?.applicationContext?.resources?.getString(R.string.switch_celsius)
                PrefsManager.INSTANCE.setTemperatureUnits("imperial")
            }else{
                switchFahrenheit.text = activity?.applicationContext?.resources?.getString(R.string.switch_fahrenheit)
                PrefsManager.INSTANCE.setTemperatureUnits("metric")
            }
        }
    }
}
